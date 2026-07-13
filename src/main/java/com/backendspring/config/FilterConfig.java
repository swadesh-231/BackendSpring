package com.backendspring.config;

import com.backendspring.filter.DummyFilter;
import com.backendspring.filter.SpringDemoFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<DummyFilter> getDummyFilterBean() {
        FilterRegistrationBean<DummyFilter> registrationBean =
                new FilterRegistrationBean<>();
        registrationBean.setFilter(new DummyFilter());
        registrationBean.setOrder(4);
        registrationBean.addUrlPatterns("/api/*", "/admin/*");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<SpringDemoFilter> getSpringDemoFilterBean() {
        FilterRegistrationBean<SpringDemoFilter> registrationBean =
                new FilterRegistrationBean<>();
        registrationBean.setFilter(new SpringDemoFilter());
        registrationBean.setOrder(5);
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }
}
