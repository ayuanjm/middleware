package com.yuan.middleware.config;

import com.yuan.middleware.interceptor.LimitFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author yuan
 * 测试jars/classes和classes/ bean加载顺序
 * 结论：先初始化自定义bean再初始化jar包 bean
 */
@Slf4j
@Configuration
public class ConfigurationBean {
    @Bean
    public String string() {
        log.info("jar包 bean初始化");
        return "yuan";
    }

    /**
     * 解决RabbitTemplate乱码
     *
     * @return
     */
    @Bean
    Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public FilterRegistrationBean limitFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new LimitFilter());
        registrationBean.setUrlPatterns(Arrays.asList("/myFilter/*"));
        return registrationBean;
    }
}
