package ru.kpfu.itis.felinooper.servlets;

import ru.kpfu.itis.felinooper.helper.CookieHelper;
import ru.kpfu.itis.felinooper.dao.impl.UserDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet(name = "loginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getServletContext().getRequestDispatcher("/login.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String cookieCheck = req.getParameter("remember");

        HttpSession session = req.getSession();

        UserDaoImpl users = new UserDaoImpl();
        if (username.equals("") || password.equals("")) {
            resp.sendRedirect("/errorlogin");
        } else {
            if (users.userIsExist(username, password)) {
                Cookie cookie = new Cookie("saved", username);
                resp.addCookie(cookie);
                if (cookieCheck != null) {
                    cookie.setMaxAge(100);
                    resp.sendRedirect("/homepage");
                } else {
                    resp.sendRedirect("/homepage");
                    cookie.setMaxAge(-1);
                }
            } else {
                resp.sendRedirect("/errorlogin");
            }
        }
    }
}
