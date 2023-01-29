package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement())
        {
            String SQL = "create table users (id bigint primary key auto_increment" +
                    ", name varchar(20) not null, lastname varchar(30) not null" +
                    ", age tinyint not null)";
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            System.out.println("Таблица уже создана!");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            String SQL = "drop table users";
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            System.out.println("Такой таблицы не существует!");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String SQL = "insert into users (name, lastname, age) values (?, ?, ?)";
        try (PreparedStatement statement = Util.getConnection().prepareStatement(SQL)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String SQL = "delete from users where id = ?";
        try (PreparedStatement statement = Util.getConnection().prepareStatement(SQL)) {
         statement.setLong(1, id);

         statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Statement statement = Util.getConnection().createStatement()) {
            String SQL = "select * from users";
            ResultSet result = statement.executeQuery(SQL);

            while (result.next()) {
                User user = new User();

                user.setId(result.getLong("id"));
                user.setName(result.getString("name"));
                user.setLastName(result.getString("lastname"));
                user.setAge(result.getByte("age"));

                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            String SQL = "delete from users";
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
