package com.backendspring.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(1)
public class ResponseHeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpServletResponse =
                (HttpServletResponse) response;

        String requestId = UUID.randomUUID().toString();

        httpServletResponse.setHeader("x-request-id", requestId);

        chain.doFilter(request, response);
    }
}
