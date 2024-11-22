package ru.romansib.otus.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(servletNames = {"AddServlet", "SubtractServlet", "MultiplyServlet", "DivServlet"})
public class CalculatorFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    private static Logger logger = LoggerFactory.getLogger(CalculatorFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        logger.info(httpServletRequest.getServletPath() + " firstNumber " + request.getParameter("first") + " secondNumber " + request.getParameter("second"));
        chain.doFilter(request, response);
    }
}
