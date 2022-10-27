package ru.kpfu.itis.felinooper.servlets;

import ru.kpfu.itis.felinooper.dao.impl.UserDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "loginCheckServlet", urlPatterns = "/logInCheck")
public class LoginCheckServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        UserDaoImpl users = new UserDaoImpl();
        resp.setCharacterEncoding("UTF-8");
        if (users.getUserByName(username) == null) {
            resp.getWriter().write("true");
        } else {
            resp.getWriter().write("false");
        }
    }
}

