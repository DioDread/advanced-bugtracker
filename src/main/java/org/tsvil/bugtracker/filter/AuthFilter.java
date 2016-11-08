/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tsvil.bugtracker.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        StringBuffer url = req.getRequestURL();
        if (url.indexOf("static") < 0) {
            HttpSession session = req.getSession();
            String contextName = req.getContextPath();
            String username = (String) session.getAttribute("username");
            if (username == null && (url.substring(url.indexOf(contextName)).length() - contextName.length() > 1)) {
                HttpServletResponse resp = (HttpServletResponse) response;
                resp.sendRedirect(filterUrl(url, contextName));
                return;
//                req.getRequestDispatcher(filterUrl(url, contextName)).forward(req, response);
            }
        }
        chain.doFilter(request, response);
    }

    private String filterUrl(StringBuffer url, String appName) {
        return url.substring(0, url.indexOf(appName) + appName.length() + 1);
    }

    @Override
    public void destroy() {
        filterConfig = null;
    }

}
