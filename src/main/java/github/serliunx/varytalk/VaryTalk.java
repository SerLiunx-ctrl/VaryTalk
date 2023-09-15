package github.serliunx.varytalk;

import github.serliunx.varytalk.common.config.autoconfiguer.SystemAutoConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({SystemAutoConfigurer.class})
public class VaryTalk {
    public static void main(String[] args) {
        SpringApplication.run(VaryTalk.class, args);
    }
}
