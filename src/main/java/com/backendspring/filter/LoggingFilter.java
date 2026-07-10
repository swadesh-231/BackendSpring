package com.backendspring.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.UUID;

@Component
@Order(2)
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        long startTime = System.currentTimeMillis();

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String requestId = UUID.randomUUID().toString();

        resp.setHeader("X-Request-ID", requestId);
        // Request log
        System.out.println("Incoming Request : "
                + req.getMethod() + " "
                + req.getRequestURI());

        try {
            chain.doFilter(request, response);
        }
        finally {
            long duration = System.currentTimeMillis() - startTime;

            // Response status log
            System.out.println("Response status: "
                    +  resp.getStatus());

            System.out.println("API Response time : " + duration);
        }
    }
}
