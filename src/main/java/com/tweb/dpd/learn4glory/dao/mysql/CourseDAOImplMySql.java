package com.tweb.dpd.learn4glory.dao.mysql;

import com.tweb.dpd.learn4glory.dao.CourseDAO;
import com.tweb.dpd.learn4glory.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete CourseDAO implementation for mysql
 */
public class CourseDAOImplMySql implements CourseDAO {

  private final String QUERY_INSERT_BY_TITLE = "INSERT INTO COURSE(TITLE) VALUES (?)";  // TODO CONTROLLARE
  private final String QUERY_INSERT_COURSE = "INSERT INTO COURSE(TITLE,ACTIVE) VALUES (?,?)";
  private final String QUERY_SELECT_BY_ID = "SELECT * FROM COURSE WHERE ID_COURSE=?";
  private final String QUERY_SELECT_BY_TITLE = "SELECT * FROM COURSE WHERE TITLE=?";
  private final String QUERY_UPDATE_ACTIVE_ID = "UPDATE COURSE SET active=? WHERE ID_COURSE=?";
  private final String QUERY_UPDATE_ACTIVE_TITLE = "UPDATE COURSE SET active=? WHERE TITLE=?";
  private final String QUERY_SELECT_ALL_COURSES = "SELECT * FROM COURSE";
  private final String QUERY_SELECT_ACTIVE_COURSES = "SELECT * FROM COURSE WHERE ACTIVE=TRUE";
  private final boolean SET_ACTIVE = true;
  private final boolean SET_DISABLED = false;

  @Override
  public int insertCourse(Course course) {

    PreparedStatement st;
    ResultSet rs;
    int result = -1;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try { // preparing query
        st = conn.prepareStatement(QUERY_INSERT_COURSE, Statement.RETURN_GENERATED_KEYS);
        st.setString(1, course.getTitle().toLowerCase());
        st.setBoolean(2, course.isActive());
        st.executeUpdate();
        rs = st.getGeneratedKeys();
        if (rs.next()) {
          result = rs.getInt(1);
        }
        st.close();
        rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
        System.out.println(e.getErrorCode() + " " + e.getMessage());
      } finally {
        // close db connection
        DAOFactoryMySql.closeDbConnection(conn);
      }
    }
    return result;
  }

  @Override
  public int insertCourse(String title) {

    PreparedStatement st;
    ResultSet rs;
    int result = -1;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try { // preparing query
        st = conn.prepareStatement(QUERY_INSERT_BY_TITLE, Statement.RETURN_GENERATED_KEYS);
        st.setString(1, title.toLowerCase());
        st.executeUpdate();
        rs = st.getGeneratedKeys();
        if (rs.next()) {
          result = rs.getInt(1);
        }
        st.close();
        rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
        System.out.println(e.getErrorCode() + " " + e.getMessage());
      } finally {
        // close db connection
        DAOFactoryMySql.closeDbConnection(conn);
      }
    }
    return result;

  }

  @Override
  public Course selectCourse(int id_course) {

    PreparedStatement ps;

    ResultSet rs;

    Course course = null;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        ps = conn.prepareStatement(QUERY_SELECT_BY_ID);
        ps.setInt(1, id_course);
        rs = ps.executeQuery();
        if (rs.next()) {
          course = new Course(rs.getInt(1), rs.getString(2), rs.getBoolean(3));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        DAOFactoryMySql.closeDbConnection(conn);
      }
    }
    return course;
  }

  @Override
  public Course selectCourse(String title) {

    PreparedStatement ps;

    ResultSet rs;

    Course course = null;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        ps = conn.prepareStatement(QUERY_SELECT_BY_TITLE);
        ps.setString(1, title.toLowerCase());
        rs = ps.executeQuery();
        if (rs.next()) {
          course = new Course(rs.getInt(1), rs.getString(2), rs.getBoolean(3));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        DAOFactoryMySql.closeDbConnection(conn);
      }
    }
    return course;
  }

  @Override
  public boolean activateCourse(int id_course) {
    return alterActiveStatus(id_course, QUERY_UPDATE_ACTIVE_ID, SET_ACTIVE);
  }

  @Override
  public boolean activateCourse(String title) {
    return alterActiveStatus(title, QUERY_UPDATE_ACTIVE_TITLE, SET_ACTIVE);
  }

  @Override
  public boolean deleteCourse(int id_course) {
    return alterActiveStatus(id_course, QUERY_UPDATE_ACTIVE_ID, SET_DISABLED);
  }

  @Override
  public boolean deleteCourse(String title) {
    return alterActiveStatus(title, QUERY_UPDATE_ACTIVE_TITLE, SET_DISABLED);
  }

  private boolean alterActiveStatus(int id_course, String query_to_alter, boolean active) {
    PreparedStatement ps;

    boolean res = false;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        ps = conn.prepareStatement(query_to_alter);
        ps.setBoolean(1, active);
        ps.setInt(2, id_course);
        res = ps.executeUpdate() > 0;
        ps.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        DAOFactoryMySql.closeDbConnection(conn);
      }
    }
    return res;
  }


  private boolean alterActiveStatus(String title, String query_to_alter, boolean activate) {
    PreparedStatement ps;

    boolean res = false;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        ps = conn.prepareStatement(query_to_alter);
        ps.setBoolean(1, activate);
        ps.setString(2, title);
        res = ps.executeUpdate() > 0;
        ps.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        DAOFactoryMySql.closeDbConnection(conn);
      }
    }
    return res;
  }

  @Override
  public List<Course> selectAllCourses() {
    return getCourses(QUERY_SELECT_ALL_COURSES);
  }

  @Override
  public List<Course> selectAllAvailableCourses() {
    return getCourses(QUERY_SELECT_ACTIVE_COURSES);
  }

  private List<Course> getCourses(String query_select_active_courses) {
    PreparedStatement ps;
    ResultSet rs;
    List<Course> courses = null;

    Connection conn = DAOFactoryMySql.openConnectionToDb();
    if (conn != null) {
      try {
        ps = conn.prepareStatement(query_select_active_courses);
        rs = ps.executeQuery();
        courses = new ArrayList<>();
        while (rs.next()) {
          Course c = new Course(rs.getInt(1), rs.getString(2), rs.getBoolean(3));
          courses.add(c);
        }
      } catch (SQLException e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
      } finally {
        DAOFactoryMySql.closeDbConnection(conn);
      }
    }
    return courses;
  }
}

