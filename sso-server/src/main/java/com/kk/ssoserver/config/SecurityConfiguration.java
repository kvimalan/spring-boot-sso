package com.kk.ssoserver.config;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @author Kuriakose V
 * @since 14/11/19.
 */
@Configuration
@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/login")
                .failureHandler(authenticationFailureHandler())
                .permitAll()
                .and()
                .logout()
                /*   .invalidateHttpSession(true)
                   .deleteCookies("JSESSIONID")
               */    .permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(loginUrlAuthenticationEntryPoint())
                .and()
                .requestMatchers().antMatchers("/mgmt/health", "/stp-localization/**", "/login", "/verifyUser", "/adminlogin", "/resetPassword", "/action/forgotPassword",
                "/oauth/authorize", "/i18n/**", "/translations/**", "/oauth/confirm_access", "/login/**", "/env/**", "/metrics/**", "/signup", "/swagger-ui.html" ,"/swagger-resources/**", "/v2/api-docs",
                "/users/resetPassword", "/users/customer","/users/verifyUser","/tokens/search/**", "/console/**", "/login.html/**")
                .and()
                .authorizeRequests().antMatchers("/mgmt/health","/stp-localization/**", "/verifyUser", "/resetPassword", "/action/forgotPassword", "/signup", "/i18n/**",
                "/translations/**", "/users/resetPassword", "/users/customer", "/users/verifyUser", "/login", "/swagger-ui.html" ,"/swagger-resources/**", "/v2/api-docs", "/tokens/search/**", "/console/**").permitAll().anyRequest().authenticated()
                .and().csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers("/users/customer")
                .ignoringAntMatchers("/action/forgotPassword");

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }


/*    @Override
    protected void configure(HttpSecurity http) throws Exception {

*//*        http.csrf().disable()
                .authorizeRequests()
                .antMatchers( "/home", "/about", "/login", "/oauth/authorize", "/h2").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(loginUrlAuthenticationEntryPoint())
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();*//*

        ;

    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("kk")
                .password("kk")
                .roles("USER", "ADMIN");

    }

    @Bean
    public LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint(){
        return new CustomLoginUrlAuthenticationEntryPoint("/login");
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler authenticationFailureHandler(){
        return new CustomLoginAuthenticationFailureHandler();
    }

    @Bean
    ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }
}