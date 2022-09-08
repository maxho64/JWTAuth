package com.example.jwtauth.config.jwt;

import com.example.jwtauth.config.CustomUserDetails;
import com.example.jwtauth.config.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@Component
public class JWTFilter extends GenericFilterBean {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final JWTProvider provider;
    private final CustomUserDetailsService service;

    @Autowired
    public JWTFilter(JWTProvider provider, CustomUserDetailsService service) {
        this.provider = provider;
        this.service = service;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
           String bearer = ((HttpServletRequest) request).getHeader(AUTHORIZATION_HEADER);
           String token = "";
           if(hasText(bearer) && bearer.startsWith("Bearer ")){
               token = bearer.substring(7);
           }

           if(!token.equals("") && provider.validate(token)){
               String login = provider.getLoginFromToken(token);
               CustomUserDetails userDetails = service.loadUserByUsername(login);
               UsernamePasswordAuthenticationToken auth =
                       new UsernamePasswordAuthenticationToken(userDetails, null,
                               userDetails.getAuthorities());
               SecurityContextHolder.getContext().setAuthentication(auth);
           }

           chain.doFilter(request, response);
    }
}
