package com.codegym.security;

import com.codegym.controller.CustomSuccessHandler;
import com.codegym.service.impl.CustomUserDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private CustomSuccessHandler customSuccessHandler;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder);
    }



    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("*");
        corsConfig.addAllowedHeader("*");
        corsConfig.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/*", corsConfig);
        return source;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().configurationSource(corsConfigurationSource());
        http.authorizeHttpRequests()
                .requestMatchers("/", "/home", "","/products/**", "/brands/**", "/categories/**").permitAll()
                .requestMatchers("/register/**").anonymous()
                .requestMatchers("/user/**", "/api/**").hasAnyRole("USER", "ADMIN", "SELLER")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/seller/**").hasRole("SELLER")
                .and()
                .formLogin().loginPage("/login")
                .loginProcessingUrl("/j_spring_security_check")
                .successHandler(customSuccessHandler)
                .failureUrl("/login?error=true")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied");

        http.rememberMe()
                .tokenRepository(this.persistentTokenRepository())
                .tokenValiditySeconds(24 * 60 * 60)
                .key("thuong")
                .rememberMeParameter("remember-me")
                .rememberMeCookieName("rememberMe");

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);

    }

    public PersistentTokenRepository persistentTokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    }
}
