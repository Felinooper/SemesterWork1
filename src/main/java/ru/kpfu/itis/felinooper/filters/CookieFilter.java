package ru.kpfu.itis.felinooper.filters;

import lombok.val;
import ru.kpfu.itis.felinooper.dao.UserDAO;
import ru.kpfu.itis.felinooper.dao.impl.UserDaoImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class CookieFilter implements Filter {

    private UserDAO userDAO;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userDAO = new UserDaoImpl();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        val cookies = httpServletRequest.getCookies();

        val session = httpServletRequest.getSession();
        if (session.getAttribute("username") != null) {
            chain.doFilter(request, response);
            return;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("saved")) {
                session.setAttribute("username", cookie.getValue());
            }
        }

        chain.doFilter(request, response);
    }
}
