package com.kk.ssoserver.config;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.security.oauth2.provider.endpoint.DefaultRedirectResolver;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.Set;

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
                .permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(loginUrlAuthenticationEntryPoint())
                .and()
                .requestMatchers().antMatchers("/login", "/oauth/authorize", "/console/**", "/login.html/**")
                .and()
                .authorizeRequests().antMatchers("/login","/console/**").permitAll().anyRequest().authenticated()
                .and().csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        http.csrf().disable();
        http.headers().frameOptions().disable();

    }


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


    @Autowired
    public void configureAuthorizationEndpoint(AuthorizationEndpoint authorizationEndpoint) {
        DefaultRedirectResolver redirectResolver = new DefaultRedirectResolver() {

            /**
             *  Override to avoid validation against client registeredRedirect uri's
             *
             * @param requestedRedirect
             * @param client
             * @return
             * @throws OAuth2Exception
             */
            @Override
            public String resolveRedirect(String requestedRedirect, ClientDetails client) throws OAuth2Exception {
                Set<String> authorizedGrantTypes = client.getAuthorizedGrantTypes();
                if (authorizedGrantTypes.isEmpty()) {
                    throw new InvalidGrantException("A client must have at least one authorized grant type.");
                }
                return requestedRedirect;
            }
        };
        redirectResolver.setMatchPorts(false);
        authorizationEndpoint.setRedirectResolver(redirectResolver);
    }

}