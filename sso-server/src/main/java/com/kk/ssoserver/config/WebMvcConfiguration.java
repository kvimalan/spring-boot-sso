package com.kk.ssoserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Kuriakose V
 * @since 02/12/19.
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/adminlogin").setViewName("adminlogin");
        registry.addViewController("/resetPassword").setViewName("resetPassword");
        registry.addViewController("/signup").setViewName("signup");
        registry.addViewController("/verifyUser").setViewName("verifyUser");
    }

}
