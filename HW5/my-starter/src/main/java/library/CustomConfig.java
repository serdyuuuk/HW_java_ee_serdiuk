package library;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@ComponentScan
@Configuration(proxyBeanMethods = false)
public class CustomConfig {


    @Bean(name = "amogus")
    @ConditionalOnMissingBean(OnMissingBeanClass.class)
    public OnMissingBeanClass onMissingBeanClass(){
        return new OnMissingBeanClass();
    }

    @Bean
    @ConditionalOnBean(name = "amogus")
    public OnBeanClass onBeanClass(){
        return new OnBeanClass();
    }

    @Bean
    @ConditionalOnProperty(prefix = "something", name = "text0")
    public OnProperty0 onProperty0(){
        return new OnProperty0();
    }

    @Bean
    @ConditionalOnProperty(prefix = "something", name = "text1")
    public OnProperty1 onProperty1(){
        return new OnProperty1();
    }
    @Bean
    @ConditionalOnProperty(prefix = "something", name = "text2")
    public OnProperty2 onProperty2(){
        return new OnProperty2();
    }
}
