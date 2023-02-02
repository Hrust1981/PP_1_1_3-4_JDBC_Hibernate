package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    private User user = null;

    @Override
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

    @Override
    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            String SQL = "drop table users";
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            System.out.println("Такой таблицы не существует!");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        user = new User(name, lastName, age);
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        user = session.get(User.class, id);
        session.remove(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        List<User> users = session.createQuery(
                "select i from User i", User.class).getResultList();
        session.getTransaction().commit();
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.createQuery("delete from User").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
