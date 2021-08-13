package ru.job4j.dream.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest sreq, ServletResponse sresp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) sreq;
        HttpServletResponse resp = (HttpServletResponse) sresp;
        String  uri = req.getRequestURI();
        if (ignore(uri)) {
            chain.doFilter(sreq, sresp);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        chain.doFilter(sreq, sresp);
    }

    private boolean ignore(String uri) {
        boolean rsl = false;
        List<String> url = List.of("auth.do", "reg.do");
        for (String el : url) {
            if (uri.endsWith(el)) {
                rsl = true;
                break;
            }
        }
        return rsl;
    }

    @Override
    public void destroy() {

    }
}
