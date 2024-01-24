package com.codegym.security;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@EnableWebSecurity
public class SecurityInit extends AbstractSecurityWebApplicationInitializer {
}
