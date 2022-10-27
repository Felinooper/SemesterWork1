package ru.kpfu.itis.felinooper.servlets;

import ru.kpfu.itis.felinooper.dao.impl.BasketDaoImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/deleteFromBasket")
public class DeleteFromBasketServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        BasketDaoImpl basketDB = new BasketDaoImpl();
        try {
            basketDB.deleteProductFromBasket(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("/basketpage");
    }
}
