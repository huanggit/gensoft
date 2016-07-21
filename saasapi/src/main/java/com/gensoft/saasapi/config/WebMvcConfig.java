package com.gensoft.saasapi.config;

import com.gensoft.core.mvc.interceptor.AuthenticateInterceptor;
import com.gensoft.core.mvc.interceptor.LoggerInterceptor;
import com.gensoft.core.mvc.resolver.LoginedArgumentResolver;
import com.gensoft.core.mvc.resolver.SaasHandlerExceptionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by alan on 16-5-25.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    LoggerInterceptor loggerInterceptor() {
        return new LoggerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerInterceptor());
    }

    //@Bean
    //SaasHandlerExceptionResolver saasHandlerExceptionResolver(){
//        return new SaasHandlerExceptionResolver();
//    }

//    @Bean
//    AuthenticateInterceptor authenticateInterceptor() {
//        return new AuthenticateInterceptor();
//    }


//    @Bean
//    public LoginedArgumentResolver loginedArgumentResolver(){
//        return new LoginedArgumentResolver();
//    }


//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        argumentResolvers.add(loginedArgumentResolver());
//    }
}
