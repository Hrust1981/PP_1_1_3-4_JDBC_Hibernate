package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("First", "First", (byte) 1);
        userService.saveUser("Second", "Second", (byte) 2);
        userService.saveUser("Third", "Third", (byte) 3);
        userService.saveUser("Fourth", "Fourth", (byte) 4);

        List<User> users = userService.getAllUsers();
        System.out.println(users);

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
