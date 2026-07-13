package com.backendspring.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Order(3)
public class ResponseBodyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletResponse httpServletResponse =
                (HttpServletResponse) servletResponse;

        ContentCachingResponseWrapper wrappedResponse =
                new ContentCachingResponseWrapper(httpServletResponse);

        filterChain.doFilter(servletRequest, wrappedResponse);

        byte[] originalBodyBytes = wrappedResponse.getContentAsByteArray();

        String originalBody = new String(originalBodyBytes, StandardCharsets.UTF_8);

        String modifiedBody =
                """
                {
                    "originalResponse": %s,
                    "appName": "Student Management System"
                }
                """.formatted(originalBody);

        wrappedResponse.resetBuffer();

        wrappedResponse.getWriter().write(modifiedBody);

        wrappedResponse.copyBodyToResponse();
    }
}
