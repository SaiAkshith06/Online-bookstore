package services;

import java.util.HashMap;
import models.User;

public class UserService {
    private HashMap<String, User> users = new HashMap<>();

    public User registerUser(String username, String email, String phoneNumber) {
        // Overwrite or create new user with full details
        User user = new User(username, email, phoneNumber);
        users.put(username, user);
        return user;
    }

    public User getUser(String username) {
        return users.get(username);
    }
}
