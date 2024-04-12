package com.april.filter;

import com.mysql.cj.util.StringUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RequestValidationBeforeFilter implements Filter {

    public static final String AUTHENTICATION_SCHEME_BASIC = "Basic";
    private Charset credentialsCharset = StandardCharsets.UTF_8;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String header = req.getHeader("Authorization");
        if (header != null) {
            header = header.trim();
            if (StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BASIC)) {
                byte[] base64Token = header.substring(6).getBytes(credentialsCharset);
                byte[] decoded;
                try {
                    decoded = Base64.getDecoder().decode(base64Token);
                    String token = new String(decoded, StandardCharsets.UTF_8);
                    int delim = token.indexOf(':');
                    if (delim == -1)
                        throw new BadCredentialsException("Invalid credentials");
                    String email = token.substring(0, delim);
                    if (email.toLowerCase().contains("test")) {
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }
                } catch (IllegalArgumentException e) {
                    throw new BadCredentialsException("failed to decode basic auth token");
                }
            }
        }
        filterChain.doFilter(req,resp);

    }
}
