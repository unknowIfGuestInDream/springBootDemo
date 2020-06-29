package com.tangl.demo.filter;


import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author: TangLiang
 * @date: 2020/6/29 14:10
 * @since: 1.0
 */
@WebFilter(filterName = "myFilter1", urlPatterns = "/hello")
public class MyFilter1 implements Filter {
    private Logger logger = Logger.getLogger(MyFilter1.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //logger.info(filterConfig.getFilterName() + " init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
//        if (true) {
//            response.sendRedirect("/error");
//        }
        //logger.info("myFilter1 begin");
        //logger.info("业务方法执行");
        filterChain.doFilter(servletRequest, servletResponse);
        //logger.info("myFilter1 end");
    }

    @Override
    public void destroy() {
        logger.info("myFilter1 destroy");
    }
}
