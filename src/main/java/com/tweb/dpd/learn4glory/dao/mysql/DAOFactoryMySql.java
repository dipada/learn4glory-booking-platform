package com.tweb.dpd.learn4glory.dao.mysql;

import com.tweb.dpd.learn4glory.dao.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Concrete DAOFactory implementation for mysql
 */
public class DAOFactoryMySql implements DAOFactory {

    private static String url = null;
    private static String user = null;
    private static String password = null;

    public DAOFactoryMySql(String urlDb, String userDb, String pwd) {
        synchronized (this) {
            url = urlDb;
            user = userDb;
            password = pwd;
        }
        registerDriver();
    }

    private static void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    /**
     * Open connection to db
     *
     * @return Connection obj if successful, null otherwise
     */
    protected static Connection openConnectionToDb() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Close passed to db connection
     */
    protected static void closeDbConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
            e.printStackTrace();
        }
    }

    /**
     * @return concrete mysql implementation for userDao class
     */
    @Override
    public UserDAO getUserDAO() {
        return new UserDAOImplMySql();
    }

    /**
     * @return concrete mysql implementation for teacherDao class
     */
    @Override
    public TeacherDAO getTeacherDAO() {
        return new TeacherDAOImplMySql();
    }

    /**
     * @return concrete mysql implementation for courseDao class
     */
    @Override
    public CourseDAO getCourseDAO() {
        return new CourseDAOImplMySql();
    }

    /**
     * @return concrete mysql implementation for lessonDAO class
     */
    @Override
    public LessonDAO getLessonDAO() {
        return new LessonDAOImplMySql();
    }

    /**
     * @return concrete mysql implementation for BookedLessonDAO class
     */
    @Override
    public BookedLessonDAO getBookedLessonDAO() {
        return new BookedLessonDAOImpMySql();
    }
}
