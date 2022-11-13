package com.tweb.dpd.learn4glory.dao.mysql;

import com.tweb.dpd.learn4glory.dao.CourseDAO;
import com.tweb.dpd.learn4glory.dao.DAOFactory;
import com.tweb.dpd.learn4glory.dao.TeacherDAO;
import com.tweb.dpd.learn4glory.dao.UserDAO;

/**
 * Concrete DAOFactory implementation for mysql
 */
public class DAOFactoryMySql implements DAOFactory {

  private final String url;
  private final String user;
  private final String password;

  public DAOFactoryMySql(String url, String user, String pwd) {
    this.url = url;
    this.user = user;
    this.password = pwd;
    System.out.println("url " + url + " user " + user + " pwd "  + pwd);
  }


  /**
   *
   * @return concrete mysql implementation for userDao class
   */
  @Override
  public UserDAO getUserDAO() {
    return new UserDAOImplMySql();
  }

  /**
   *
   * @return concrete mysql implementation for teacherDao class
   */
  @Override
  public TeacherDAO getTeacherDAO() {
    return new TeacherDAOImplMySql();
  }

  /**
   *
   * @return concrete mysql implementation for courseDao class
   */
  @Override
  public CourseDAO getCourseDAO() {
    return new CourseDAOImplMySql();
  }
}
