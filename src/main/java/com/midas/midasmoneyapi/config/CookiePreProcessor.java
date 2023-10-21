package com.midas.midasmoneyapi.config;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CookiePreProcessor implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;

        if("/oauth/token".equalsIgnoreCase(((HttpServletRequest) servletRequest).getRequestURI())
                                && "refresh_token".equals(servletRequest.getParameter("grant_type"))
                                && req.getCookies() != null){
            for(Cookie c : req.getCookies()){
                if(c.getName().equals("refreshToken")){
                    String refreshToken = c.getValue();
                    req = new MyServletRequest(req, refreshToken);
                }
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    static class MyServletRequest extends HttpServletRequestWrapper {
        private String refreshToken;

        public MyServletRequest(HttpServletRequest request, String refreshToken) {
            super(request);
            this.refreshToken = refreshToken;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
            map.put("refresh_token", new String[] {refreshToken});
            map.setLocked(true);
            return map;
        }
    }
}
