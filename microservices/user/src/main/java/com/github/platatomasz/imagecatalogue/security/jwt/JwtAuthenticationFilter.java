package com.github.platatomasz.imagecatalogue.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private final JwtService jwtService;
    
    @Autowired
    // USE CUSTOM ONE!!!
    private final UserDetailsService userDetailsService;
    
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String BEARER_PREFIX = "Bearer ";
        
        final String authorizationHeader = request.getHeader("Authorization"); // JWT token from request header = "Bearer " + JWT_TOKEN
        final String jwtToken;
        final String userEmail;
        
        if(authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        jwtToken = authorizationHeader.substring(BEARER_PREFIX.length()); // Extracted token
        userEmail = jwtService.extractUsername(jwtToken);// TODO Extract from JWT token
        
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if(jwtService.isTokenValid(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
                );
                
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        
        filterChain.doFilter(request, response);
    }
    
}
