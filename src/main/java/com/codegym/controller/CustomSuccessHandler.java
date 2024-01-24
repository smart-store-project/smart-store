package com.codegym.controller;


import com.codegym.model.dto.UserDto;
import com.codegym.service.IUserService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@SessionAttributes("user")
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private IUserService2 userService2;

    @ModelAttribute("user")
    public UserDto currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userService2.findByUsername(userDetails.getUsername()).orElse(null);
        } else {
            return null;
        }
    }


    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(authentication);
        HttpSession session = request.getSession();
        session.setAttribute("user", currentUser());
        if (response.isCommitted()) {
            System.out.println("Can't redirect");
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);

    }

    protected String determineTargetUrl(Authentication authentication) {
        String url;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<>();

        for (GrantedAuthority authority : authorities) {
            roles.add(authority.getAuthority());
        }

        if (isAdmin(roles)) {
            url = "/admin";
        } else if (isSeller(roles)) {
            url = "/seller";
        } else {
            url = "/user";
        }
        return url;
    }

    private boolean isSeller(List<String> roles) {
        return roles.contains("ROLE_SELLER");
    }

    private boolean isAdmin(List<String> roles) {
        return roles.contains("ROLE_ADMIN");
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
}
