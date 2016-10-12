package org.tsvil.bugtracker.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class RequestParameterDecoder implements Filter {

    /**
     * The default character encoding to set for request / response.
     */
    private String encoding = null;

    /**
     * The filter configuration object.
     */
    private FilterConfig filterConfig;

    /**
     * Should a character encoding specified by the client be ignored?
     */
    private boolean ignore = true;

    @Override
    public void destroy() {
        encoding = null;
        filterConfig = null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        // conditionally select and set the character encoding to be used
        if ((ignore || (request.getCharacterEncoding() == null)) && (encoding != null)) {
            request.setCharacterEncoding(encoding);
            response.setCharacterEncoding(encoding);
        }

        // pass control on to the next filter
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        this.encoding = filterConfig.getInitParameter("encoding");

        String value = filterConfig.getInitParameter("ignore");

        this.ignore = ((value == null) || value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes"));
    }
}
