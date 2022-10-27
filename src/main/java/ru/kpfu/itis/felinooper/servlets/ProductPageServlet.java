package ru.kpfu.itis.felinooper.servlets;

import ru.kpfu.itis.felinooper.model.Product;
import ru.kpfu.itis.felinooper.dao.impl.ProductDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/productpage")
public class ProductPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        ProductDaoImpl productDB = new ProductDaoImpl();
        Product product = productDB.getProductByID(id);
        req.setAttribute("product", product);

        req.getServletContext().getRequestDispatcher("/productpage.ftl").forward(req, resp);
    }
}