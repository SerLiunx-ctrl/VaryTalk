package com.serliunx.varytalk.framework.httpclient.support;

import com.serliunx.varytalk.framework.httpclient.annotation.Client;
import com.serliunx.varytalk.framework.httpclient.decoder.JacksonDecoder;
import com.serliunx.varytalk.framework.httpclient.encoder.JacksonEncoder;
import feign.Feign;
import feign.Request;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.env.Environment;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Http客户端bean注册
 * @author SerLiunx
 * @since 1.0
 * @see BeanDefinitionRegistryPostProcessor
 */
@Component
@SuppressWarnings("all")
public final class HttpClientBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor,
        EnvironmentAware {

    /**
     * 按照Spring的初始化顺序、无法使用Value注入值. 此时该注解对应的处理器还未加载
     * 所以此处使用环境变量注入值.
     * @see EnvironmentAware
     */
    private Environment environment;

    private final Class<? extends Annotation> annotatedType = Client.class;
    private final InterfaceWithAnnotationTypeFilter interfaceWithAnnotationTypeFilter =
            new InterfaceWithAnnotationTypeFilter(Client.class);
    private final Encoder encoder = new JacksonEncoder();
    private final Decoder decoder = new JacksonDecoder();

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {}

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        ClassPathClientScanner scanner = new ClassPathClientScanner(registry);
        scanner.addIncludeFilter(interfaceWithAnnotationTypeFilter);
        String basePackage = environment.getProperty("talk-system.http-client.base-package");
        Set<BeanDefinition> components = scanner.findCandidateComponents(basePackage);
        //扫描、逐一注册符合条件的HttpClient客户端
        for (BeanDefinition component : components) {
            BeanDefinitionHolder beanDefinitionHolder = processBeanDefinition(component);
            registry.registerBeanDefinition(beanDefinitionHolder.getBeanName(),
                    beanDefinitionHolder.getBeanDefinition());
        }
    }

    private BeanDefinitionHolder processBeanDefinition(BeanDefinition beanDefinition){
        try {
            Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
            if(!clazz.isInterface()){
                throw new RuntimeException("无法注册非接口类型的Feign客户端!");
            }
            Client client = clazz.getAnnotation(Client.class);
            if(client == null){
                throw new RuntimeException("该接口未包含指定的注解: " + client.getClass().getName());
            }
            //获取注解的url
            String url = client.url();

            //初始化bean定义
            GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition(beanDefinition);
            genericBeanDefinition.setBeanClass(clazz);

            //设置bean对象
            genericBeanDefinition.setInstanceSupplier(() -> {
                return build(clazz, url);
            });

            //注册bean、生成bean名称
            String name = client.value().isEmpty() ? generateBeanName(beanDefinition) : client.value();
            return new BeanDefinitionHolder(genericBeanDefinition, name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将类名的首字母小写作为bean的名称
     */
    private String generateBeanName(BeanDefinition beanDefinition){
        String[] splitName = beanDefinition.getBeanClassName().toString().split("\\.");
        String name = splitName[splitName.length - 1];
        if (name == null || name.isEmpty()) {
            return name;
        }
        char[] charArray = name.toCharArray();
        charArray[0] = Character.toLowerCase(charArray[0]);
        return new String(charArray);
    }

    /**
     * 使用Feign的Builder来构建客户端
     */
    private <T> T build(Class<T> clazz, String url){
        return Feign.builder()
                .encoder(encoder)
                .decoder(decoder)
                .options(new Request.Options(10,
                        TimeUnit.SECONDS, 10,
                        TimeUnit.SECONDS, true))
                .target(clazz, url);
    }

    /**
     * HTTP客户端扫描器
     */
    private static class ClassPathClientScanner extends ClassPathBeanDefinitionScanner {

        public ClassPathClientScanner(BeanDefinitionRegistry registry) {
            super(registry, false);
        }

        /**
         * 重写Bean定义的条件、让其可以扫描到接口
         */
        @Override
        protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
            return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
        }
    }

    /**
     * 自定义一个可以扫描接口注解的的过滤器
     */
    private static class InterfaceWithAnnotationTypeFilter implements TypeFilter {
        private final Class<? extends Annotation> annotationType;

        public InterfaceWithAnnotationTypeFilter(Class<? extends Annotation> annotationType) {
            this.annotationType = annotationType;
        }

        @Override
        public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
            Class<?> targetClass;
            try {
                targetClass = Class.forName(metadataReader.getClassMetadata().getClassName());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            return targetClass.isInterface() && targetClass.isAnnotationPresent(annotationType);
        }
    }
}
