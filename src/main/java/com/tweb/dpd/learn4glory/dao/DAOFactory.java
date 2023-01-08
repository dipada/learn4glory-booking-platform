package com.tweb.dpd.learn4glory.dao;

import com.tweb.dpd.learn4glory.dao.mysql.DAOFactoryMySql;

/**
 * Interface that provide method for return correct implementation of a factory.
 * Uses strategy and factory GOF patterns.
 * Modify this interface for add other datasource at application.
 * Datasource are dynamically loaded at application startup.
 *
 * @author dani9
 * <p>
 * ref: <a href="https://www.oracle.com/java/technologies/dataaccessobject.html">reference</a>
 */
public interface DAOFactory {

  // List of DAO types supported by the factory
  int MYSQL = 1;
  int CLOUDSCAPE = 2;

  // There will be a method for each DAO that can be
  // created. The concrete factories will have to
  // implement these methods.
  UserDAO getUserDAO();

  TeacherDAO getTeacherDAO();

  CourseDAO getCourseDAO();

  LessonDAO getLessonDAO();

  BookedLessonDAO getBookedLessonDAO();


  //public abstract AccountDAO getAccountDAO();

  //public abstract OrderDAO getOrderDAO();

  /**
   * Modify if wanna add a datasource to application
   *
   * @param whichFactory  use interface constant to identify a datasource
   * @param argsToFactory args used by different datasource
   * @return implementation of daoFactory for a specified datasource, null otherwise
   */
  static DAOFactory getDAOFactory(int whichFactory, String[] argsToFactory) {
    switch (whichFactory) {
      case MYSQL: {
        if (argsToFactory.length == 3) {
          return new DAOFactoryMySql(argsToFactory[0], argsToFactory[1], argsToFactory[2]);
        } else {
          return null;
        }
      }
      // QUI ALTRI POSSIBILI DATASOURCE
      case CLOUDSCAPE:
        return null;

      default:
        return null;
    }
  }
}
