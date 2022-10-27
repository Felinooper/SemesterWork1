package ru.kpfu.itis.felinooper.dao.impl;

import ru.kpfu.itis.felinooper.dao.UserDAO;
import ru.kpfu.itis.felinooper.helper.PasswordHelper;
import ru.kpfu.itis.felinooper.helper.PostgresConnectionHelper;
import ru.kpfu.itis.felinooper.helper.UserHelper;
import ru.kpfu.itis.felinooper.model.User;

import java.sql.*;


public class UserDaoImpl implements UserDAO {

    private static final String SQL_INSERT_INTO_USERS
            = "INSERT INTO users(username, password, registration_time) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_USER_BY_ID
            = "SELECT * FROM users where id = ?";
    private static final String SQL_SELECT_USER_BY_USERNAME
            = "SELECT * FROM users where username LIKE ?";
    private static final String SQL_SELECT_USER_BY_USERNAME_AND_PASSWORD
            = "SELECT * FROM users where username LIKE ? AND password LIKE ?";
    private static final String SQL_SET_EMAIL_BY_ID
            = "UPDATE users SET email = ? WHERE id = ?";
    private static final String SQL_SET_USERNAME_BY_ID
            = "UPDATE users SET username = ? WHERE id = ?";
    private final Connection connection = PostgresConnectionHelper.getConnection();

    @Override
    public boolean saveUser(String username, String password) {

        User user = UserHelper.createNewUser(username, password);

        if (user == null) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_USERS)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    public User getUserByID(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_ID)) {
            preparedStatement.setInt(1, id);
            return getUserFromDB(preparedStatement);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserByName(String username) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_USERNAME)) {
            preparedStatement.setString(1, username);

            return getUserFromDB(preparedStatement);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean userIsExist(String username, String password) {
        if (username == null || password == null) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_USERNAME_AND_PASSWORD)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, PasswordHelper.encrypt(password));

            if (getUserFromDB(preparedStatement) != null) {
                return true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    private User getUserFromDB(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        User user = null;
        if (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setEmail(resultSet.getString("email"));
            user.setRegistrationTime(resultSet.getDate("registration_time"));
        }
        return user;
    }

    public boolean changeEmailById(int id, String email) throws SQLException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SET_EMAIL_BY_ID)) {
            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            return true;
        }
    }

    public boolean changeUsernameById(int id, String username) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SET_USERNAME_BY_ID)) {
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            return true;
        }
    }
}