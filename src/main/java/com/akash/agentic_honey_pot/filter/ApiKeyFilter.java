package com.akash.agentic_honey_pot.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    @Value("${security.api-key}")
    private String apiKey;

    private static final String HEADER_NAME="x-api-key";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

//        String requestApiKey = request.getHeader(HEADER_NAME);
//
//        if(requestApiKey == null || !requestApiKey.equals(apiKey)){
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }

        filterChain.doFilter(request, response);
    }
}
