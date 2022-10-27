package ru.kpfu.itis.felinooper.dao;

import ru.kpfu.itis.felinooper.model.User;

import java.sql.SQLException;

public interface UserDAO {
    boolean saveUser(String username, String password);
    User getUserByID(int id);
    User getUserByName(String username) throws SQLException;
    boolean userIsExist(String username, String password);
    boolean changeUsernameById(int id, String username) throws SQLException;
    boolean changeEmailById(int id, String email) throws SQLException;
}