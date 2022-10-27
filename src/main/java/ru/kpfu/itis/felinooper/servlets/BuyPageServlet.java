package ru.kpfu.itis.felinooper.servlets;

import ru.kpfu.itis.felinooper.model.Product;
import ru.kpfu.itis.felinooper.dao.impl.ProductDaoImpl;
import ru.kpfu.itis.felinooper.dao.impl.PurchasesDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/buypage")
public class BuyPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");

        PurchasesDaoImpl purchasesDaoImpl = new PurchasesDaoImpl();
        List<Product> products = null;
        try {
            products = purchasesDaoImpl.getLastPurchaseList(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("products", products);

        req.getServletContext().getRequestDispatcher("/buypage.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        List<Product> products = (List<Product>) session.getAttribute("products");
        ProductDaoImpl productDB = new ProductDaoImpl();

        for (Product product: products) {
            try {
                productDB.setMark(Integer.parseInt(req.getParameter("evaluation" + product.getId())), product.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        resp.sendRedirect("/prof");
    }
}