package ru.kpfu.itis.felinooper.servlets;

import ru.kpfu.itis.felinooper.model.Product;
import ru.kpfu.itis.felinooper.dao.impl.PurchasesDaoImpl;
import ru.kpfu.itis.felinooper.model.User;
import ru.kpfu.itis.felinooper.dao.impl.UserDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "profileServlet", urlPatterns = "/prof")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");

        UserDaoImpl users = new UserDaoImpl();
        User user = users.getUserByName(username);
        String registrationTime = user.getRegistrationTime().toString();
        String email = user.getEmail();

        PurchasesDaoImpl purchasesDaoImpl = new PurchasesDaoImpl();
        int purchasesCount = 0;
        try {
            purchasesCount = purchasesDaoImpl.getCountOfPurchase(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Product lastProduct = null;
        try {
            lastProduct = purchasesDaoImpl.getLastPurchase(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.setAttribute("purchasesCount", purchasesCount);
        if (lastProduct == null) {
            req.setAttribute("lastProductName", "-");
        } else {
            req.setAttribute("lastProductName", lastProduct.getName());
        }
        req.setAttribute("registrationTime", registrationTime);
        if (email == null) {
            req.setAttribute("email", "-");
        } else {
            req.setAttribute("email", email);
        }

        req.getServletContext().getRequestDispatcher("/profilepage.ftl").forward(req, resp);
    }

}
