package com.yuan.middleware.config;

import com.yuan.middleware.interceptor.RequestHandleTimeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author yuan
 * 使用WebMvcConfigurerAdapter扩展SpringMvc的功能
 */
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * HandlerInterceptor里@Autowired对象为空的解决方法
     * @return
     */
    @Bean
    public RequestHandleTimeInterceptor requestHandleTimeInterceptor(){
        return new RequestHandleTimeInterceptor();
    }
    /**
     * 所有的WebMvcConfigurerAdapter组件都会一起起作用
     * 将组件注册到容器中
     *
     * @return
     */
    @Bean
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                /**
                 * addViewControllers可以方便的实现一个请求直接映射成视图，而无需书写controller
                 * registry.addViewController("请求路径").setViewName("请求页面文件路径")
                 */
                registry.addViewController("/").setViewName("upload");

            }

            //注册拦截器：相当于<mvc:interceptors>
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(requestHandleTimeInterceptor()).addPathPatterns("/**")
                        .excludePathPatterns("/webjars/**");
            }
        };

        return adapter;
    }
}
