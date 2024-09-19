package com.dxc.accountservice.configuration.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//@Component
public class AccountFilter {
//
//    private static final Logger logger = LoggerFactory.getLogger(AccountFilter.class);
//
//    private List<String> ips;
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
//        ips = List.of("127.0.0.1", "0.0.0.0", "192.168.0.1","192.168.240.48","0:0:0:0:0:0:0:1");
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        String ip_ = servletRequest.getLocalAddr();
//        boolean isOk = ips.stream().filter(ip -> ip_.equals(ip)).findFirst().isPresent();
//        if (!isOk) {
//            ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_FORBIDDEN);
//            return;
//        }
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//
//    @Override
//    public void destroy() {
//        Filter.super.destroy();
//    }
//
//    private String getRemoteAddr(HttpServletRequest request) {
//        String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
//        if (ipFromHeader != null && ipFromHeader.length() > 0) {
//            logger.debug("ip from proxy - X-FORWARDED-FOR : " + ipFromHeader);
//            return ipFromHeader;
//        }
//        return request.getRemoteAddr();
//    }
}
