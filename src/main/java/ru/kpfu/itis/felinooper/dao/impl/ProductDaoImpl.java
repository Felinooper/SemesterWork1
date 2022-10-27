package ru.kpfu.itis.felinooper.dao.impl;

import ru.kpfu.itis.felinooper.dao.ProductDAO;
import ru.kpfu.itis.felinooper.helper.PostgresConnectionHelper;
import ru.kpfu.itis.felinooper.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDAO {

    private final Connection connection = PostgresConnectionHelper.getConnection();

    private static final String SQL_SELECT_PRODUCT_BY_ID
            = "SELECT id, name, description, composer, image, price, count_of_marks, rating FROM products JOIN product_rating ON product_id = id WHERE id = ?";

    private static final String SQL_CHANGE_RATING_BY_PRODUCT_ID = "UPDATE product_rating SET count_of_marks = ?, rating = ? WHERE product_id = ?";

    private static final String SQL_GET_PRODUCTS = "SELECT * FROM products";

    public Product getProductByID(int id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_PRODUCT_BY_ID)){
            preparedStatement.setInt(1, id);
            return getProductFromDB(preparedStatement);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private Product getProductFromDB(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        Product product = null;

        if (resultSet.next()){
            product = new Product();
            product.setId(resultSet.getInt("id"));
            product.setName(resultSet.getString("name"));
            product.setDescription(resultSet.getString("description"));
            product.setImage(resultSet.getString("image"));
            product.setComposer(resultSet.getString("composer"));
            product.setPrice(resultSet.getDouble("price"));
            product.setCountOfMark(resultSet.getInt("count_of_marks"));
            product.setRating(resultSet.getDouble("rating"));
        }

        return product;
    }

    private Product getProductFromDB(ResultSet resultSet) throws SQLException {
        Product product = null;

            product = new Product();
            product.setId(resultSet.getInt("id"));
            product.setName(resultSet.getString("name"));
            product.setDescription(resultSet.getString("description"));
            product.setImage(resultSet.getString("image"));
            product.setComposer(resultSet.getString("composer"));
            product.setPrice(resultSet.getDouble("price"));
            //product.setCountOfMark(resultSet.getInt("count_of_marks"));
            //product.setRating(resultSet.getDouble("rating"));
            product.setLinkAppleMusic(resultSet.getString("link"));


        return product;
    }

    public boolean setMark(int mark, int productId) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_PRODUCT_BY_ID)) {
            preparedStatement.setInt(1, productId);
            Product product = getProductFromDB(preparedStatement);
            double currentRating = product.getRating();
            int currentCountOfMark = product.getCountOfMark();
            try (PreparedStatement preparedStatementForRating = connection.prepareStatement(SQL_CHANGE_RATING_BY_PRODUCT_ID)) {
                preparedStatementForRating.setInt(1, currentCountOfMark + 1);
                preparedStatementForRating.setDouble(2, (currentRating*currentCountOfMark + mark)/(currentCountOfMark + 1));
                preparedStatementForRating.setInt(3, productId);
                preparedStatementForRating.executeUpdate();
            }
            return true;
        }
    }

    public List<Product> getProductList() {
        List<Product> productList = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_PRODUCTS)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productList.add(getProductFromDB(resultSet));
            }
            return productList;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}

