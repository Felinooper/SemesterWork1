package ru.kpfu.itis.felinooper.servlets;

import lombok.val;
import ru.kpfu.itis.felinooper.dao.ProductDAO;
import ru.kpfu.itis.felinooper.dao.impl.ProductDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "homePageServlet", urlPatterns = "/homepage")
public class HomePageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("saved")) {
                session.setAttribute("username", cookie.getValue());
            }
        }

        val productDAO = new ProductDaoImpl();
        req.setAttribute("products", productDAO.getProductList());

        req.getServletContext().getRequestDispatcher("/homepage.ftl").forward(req, resp);
    }
}
