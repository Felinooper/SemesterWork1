package ru.kpfu.itis.felinooper.dao.impl;

import ru.kpfu.itis.felinooper.dao.BasketDAO;
import ru.kpfu.itis.felinooper.helper.PostgresConnectionHelper;
import ru.kpfu.itis.felinooper.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class BasketDaoImpl implements BasketDAO {
    private final Connection connection = PostgresConnectionHelper.getConnection();

    private static final String SQL_INSERT_INTO_USERBASKET
            = "INSERT INTO user_basket(username, product_id) VALUES (?, ?)";

    private static final String SQL_SELECT_PRODUCTS_FROM_BASKET_BY_USERNAME
            = "SELECT id, name, description, composer, image, price, count_of_marks, rating FROM products JOIN product_rating ON product_rating.product_id = products.id JOIN user_basket ON user_basket.product_id = products.id WHERE username = ?";

    private static final String SQL_DELETE_PRODUCT_FROM_BASKET
            = "DELETE FROM user_basket WHERE product_id = ?";

    private static final String SQL_CLEAN_BASKET_FOR_USER
            = "DELETE FROM user_basket WHERE username = ?";

    public boolean saveProductToBasket(String username, int productId){
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_USERBASKET)) {
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, productId);

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public LinkedList<Product> getListOfProductsInBasketByUsername(String username) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_PRODUCTS_FROM_BASKET_BY_USERNAME)) {
            preparedStatement.setString(1, username);
            return getListOfProductsFromDB(preparedStatement);
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

    public boolean deleteProductFromBasket(int productId) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_PRODUCT_FROM_BASKET)) {
            preparedStatement.setInt(1, productId);
            preparedStatement.executeUpdate();
            return true;
        }
    }

    public boolean cleanBasketForUser(String username) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CLEAN_BASKET_FOR_USER)) {
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
            return true;
        }
    }
}
