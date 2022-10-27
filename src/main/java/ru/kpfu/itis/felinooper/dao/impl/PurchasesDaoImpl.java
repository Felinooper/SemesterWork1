package ru.kpfu.itis.felinooper.dao.impl;

import ru.kpfu.itis.felinooper.dao.PurchasesDAO;
import ru.kpfu.itis.felinooper.helper.PostgresConnectionHelper;
import ru.kpfu.itis.felinooper.model.Product;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PurchasesDaoImpl implements PurchasesDAO {
    private final Connection connection = PostgresConnectionHelper.getConnection();

    private static final String SQL_INSERT_PRODUCT_IN_PURCHASES
            = "INSERT INTO users_purchases(username, product_id, purchase_timestamp) VALUES (?, ?, ?)";

    private static final String SQL_SELECT_PURCHASE_TIMESTAMP_OF_LAST_PURCHASE
            = "SELECT purchase_timestamp FROM users_purchases WHERE username = ? ORDER BY purchase_timestamp DESC LIMIT 1";

    private static final String SQL_SELECT_LAST_PURCHASE_BY_USERNAME
            = "SELECT id, name, description, composer, image, price, count_of_marks, rating FROM products JOIN product_rating ON product_rating.product_id = products.id JOIN users_purchases ON users_purchases.product_id = products.id WHERE username = ? AND purchase_timestamp = ?";

    private static final String SQL_SELECT_PURCHASES_BY_USERNAME
            = "SELECT id, name, description, composer, image, price, count_of_marks, rating FROM products JOIN product_rating ON product_rating.product_id = products.id JOIN users_purchases ON users_purchases.product_id = products.id WHERE username = ?";

    public boolean insertProductsIntoDB(String username, List<Product> products) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_PRODUCT_IN_PURCHASES)) {
            for (Product product : products) {
                preparedStatement.setString(1, username);
                preparedStatement.setInt(2, product.getId());
                preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

                preparedStatement.executeUpdate();
            }
            return true;
        }
    }

    public List<Product> getLastPurchaseList(String username) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_LAST_PURCHASE_BY_USERNAME)) {
            Timestamp timestamp = getLastTimestamp(username);

            preparedStatement.setString(1, username);
            preparedStatement.setTimestamp(2, timestamp);

            return getListOfProductsFromDB(preparedStatement);
        }
    }

    public Product getLastPurchase(String username) throws SQLException {
        if (getLastPurchaseList(username).isEmpty()) {
            return null;
        } else {
            return getLastPurchaseList(username).get(0);
        }
    }

    public int getCountOfPurchase(String username) throws SQLException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_PURCHASES_BY_USERNAME)) {
            preparedStatement.setString(1, username);

            return getListOfProductsFromDB(preparedStatement).size();
        }
    }

    private Timestamp getLastTimestamp(String username) throws SQLException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_PURCHASE_TIMESTAMP_OF_LAST_PURCHASE)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            Timestamp timestamp = null;

            if (resultSet.next()) {
                timestamp = resultSet.getTimestamp("purchase_timestamp");
            }

            return timestamp;
        }
    }

    private LinkedList<Product> getListOfProductsFromDB(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        Product product = null;
        LinkedList<Product> listOfProducts = new LinkedList<>();

        while (resultSet.next()){
            product = new Product();
            product.setId(resultSet.getInt("id"));
            product.setName(resultSet.getString("name"));
            product.setDescription(resultSet.getString("description"));
            product.setImage(resultSet.getString("image"));
            product.setComposer(resultSet.getString("composer"));
            product.setPrice(resultSet.getDouble("price"));
            product.setCountOfMark(resultSet.getInt("count_of_marks"));
            product.setRating(resultSet.getDouble("rating"));

            listOfProducts.add(product);
        }

        return listOfProducts;
    }
}
