package ru.kpfu.itis.felinooper.helper;

import ru.kpfu.itis.felinooper.model.User;

public class UserHelper {

    public static User createNewUser(String username, String password){
        if (username == null || password == null) {
            return null;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(PasswordHelper.encrypt(password));
        return user;
    }
}
