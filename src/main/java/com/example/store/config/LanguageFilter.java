package com.example.store.config;


import com.example.store.util.LanguageContext;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter("/*")
public class LanguageFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Do nothing here
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // Lấy ngôn ngữ từ header Accept-Language của request
        String language = httpRequest.getHeader("Accept-Language");
        if (language != null) {
            // Lưu ngôn ngữ vào LanguageContext
            LanguageContext.setLanguage(language);
        }

        // Tiếp tục chuỗi xử lý filter
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Do nothing here
    }
}
