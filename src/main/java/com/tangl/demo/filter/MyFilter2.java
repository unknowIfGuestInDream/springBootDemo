package com.tangl.demo.filter;

import javax.servlet.*;

/**
 * @author: TangLiang
 * @date: 2020/6/29 14:20
 * @since: 1.0
 */
public class MyFilter2 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //System.out.println(filterConfig.getFilterName() + " init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        //System.out.println("myFilter2 begin");
        try {
            //System.out.println("业务方法执行");
            chain.doFilter(request, response);
        } catch (Exception e) {
            //System.out.println("myFilter2 error");
        }
        //System.out.println("myFilter2 end");
    }

    @Override
    public void destroy() {
    }
}
