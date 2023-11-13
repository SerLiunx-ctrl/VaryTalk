package com.serliunx.varytalk.common.util;

import com.serliunx.varytalk.common.exception.ServiceException;
import org.aspectj.lang.JoinPoint;
import org.springframework.aop.SpringProxy;
import org.springframework.aop.TargetClassAware;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.target.SingletonTargetSource;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class AopUtils {
    private AopUtils(){}

    /**
     * 根据传入的对象获取对应的类(数组)
     * @param objects 对象
     * @return 类数组
     */
    public static Class<?>[] getClassArray(Object[] objects){
        Class<?>[] classes = new Class[objects.length];
        for (int i = 0; i < objects.length; i++) {
            classes[i] = objects[i].getClass();
        }
        return classes;
    }

    public static Class<?> ultimateTargetClass(Object candidate) {
        Assert.notNull(candidate, "Candidate object must not be null");
        Object current = candidate;
        Class<?> result = null;
        while (current instanceof TargetClassAware targetClassAware) {
            result = targetClassAware.getTargetClass();
            current = getSingletonTarget(current);
        }
        if (result == null) {
            result = (org.springframework.aop.support.AopUtils.isCglibProxy(candidate) ? candidate.getClass().getSuperclass() : candidate.getClass());
        }
        return result;
    }

    public static Object getSingletonTarget(Object candidate) {
        if (candidate instanceof Advised advised) {
            TargetSource targetSource = advised.getTargetSource();
            if (targetSource instanceof SingletonTargetSource singleTargetSource) {
                return singleTargetSource.getTarget();
            }
        }
        return null;
    }

    public static boolean isCglibProxy(@Nullable Object object) {
        return (object instanceof SpringProxy &&
                object.getClass().getName().contains(ClassUtils.CGLIB_CLASS_SEPARATOR));
    }

    /**
     * 获取参数信息
     * @param method 方法
     * @param type 参数类型
     * @return 参数信息
     */
    public static Parameter getParameter(Method method, Class<?> type){
        for (Parameter parameter : method.getParameters()) {
            if(parameter.getClass().equals(type))
                return parameter;
        }
        return null;
    }

    /**
     * 获取参数信息
     * @param method 方法
     * @param paraName 参数名
     * @return 参数信息
     */
    public static Parameter getParameter(Method method,String paraName){
        for (Parameter parameter : method.getParameters()) {
            if(parameter.getName().equals(paraName))
                return parameter;
        }
        return null;
    }

    /**
     * 根据类型从数组中获取数据
     * @param args 数组
     * @param paraType 类型
     * @return 数据
     */
    public static Object getArg(Object[] args, Class<?> paraType){
        for (Object arg : args) {
            if(arg.getClass().equals(paraType)){
                return arg;
            }
        }
        return null;
    }

    /**
     * 通过参数数组、参数值构建一个参数名称和值的Map
     * @param parameters 参数数组
     * @param args 参数值数组
     * @return Map
     */
    public static Map<String, Object> buildArgsMap(Parameter[] parameters, Object[] args){
        Map<String, Object> argsMap = new HashMap<>();
        for (int i = 0; i < parameters.length; i++) {
            argsMap.put(parameters[i].getName(), args[i]);
        }
        return argsMap;
    }

    /**
     * 根据切入点获取切入方法
     * @param joinPoint 切入点
     * @return 方法
     *
     * @deprecated 此方法效率低且没必要, 请使用JoinPoint.getSignature(MethodSignature实现类)来获取方法
     */
    @Deprecated(since = "1.0")
    public static Method getMethodByJoinPoint(JoinPoint joinPoint){
        Object target = joinPoint.getTarget();
        Class<?> pointClass = target.getClass();
        //切入点方法名
        String pointName = joinPoint.getSignature().getName();
        Class<?>[] argClasses = AopUtils.getClassArray(joinPoint.getArgs());
        try {
            return pointClass.getMethod(pointName, argClasses);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkIfNull(Object[] objects){
        boolean result = true;
        for (Object object : objects) {
            if (object != null) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 获取指定名称的属性
     * <p>
     * 该类找不到则可以向上查找父类是否存在该属性.
     * @param clazz 类型
     * @param name 属性名称
     * @param findInSuper 是否向上查找父类
     * @return 属性
     */
    public static Field getField(Class<?> clazz, String name, boolean findInSuper){
        if(clazz == null){
            return null;
        }
        try{
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                if(field.getName().equals(name)){
                    return field;
                }
            }
            if(findInSuper){
                //递归调用向上查找父类
               return getField(clazz.getSuperclass(), name, true);
            }
        }catch (Exception e){
            throw new ServiceException(e.getClass() + ": " + e.getMessage(), 400);
        }
        return null;
    }
}
