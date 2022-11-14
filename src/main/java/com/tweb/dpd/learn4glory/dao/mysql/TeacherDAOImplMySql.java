package com.tweb.dpd.learn4glory.dao.mysql;

import com.tweb.dpd.learn4glory.dao.TeacherDAO;
import com.tweb.dpd.learn4glory.model.Course;
import com.tweb.dpd.learn4glory.model.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete TeacherDAO implementation for mysql
 */
public class TeacherDAOImplMySql implements TeacherDAO {
  private final String QUERY_INSERT_TEACHER = "INSERT INTO TEACHER(SURNAME, NAME, ACTIVE) VALUES (?,?,?)";
  private final String QUERY_SELECT_TEACHER_ID = "SELECT * FROM TEACHER WHERE ID_TEACHER=?";
  private final String QUERY_SELECT_TEACHER_SURNAME_NAME = "SELECT * FROM TEACHER WHERE SURNAME=? AND NAME=?";
  private final String QUERY_UPDATE_ACTIVE_ID = "UPDATE TEACHER SET active=? WHERE ID_TEACHER=?";
  private final boolean SET_ACTIVE = true;
  private final boolean SET_DISABLED = false;
  private final String QUERY_SELECT_ALL_TEACHER = "SELECT * FROM TEACHER";
  private final String QUERY_SELECT_ALL_ACTIVE_TEACHER = "SELECT * FROM TEACHER WHERE ACTIVE=TRUE";

  @Override
  public int insertTeacher(Teacher teacher) {
    PreparedStatement st;
    ResultSet rs;
    int result = -1;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try { // preparing query
        st = conn.prepareStatement(QUERY_INSERT_TEACHER, Statement.RETURN_GENERATED_KEYS);
        st.setString(1, teacher.getSurname());
        st.setString(2, teacher.getName());
        st.setBoolean(3, teacher.isActive());
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
  public Teacher selectTeacher(int id_teacher) {
    PreparedStatement ps;
    ResultSet rs;
    Teacher teacher = null;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try { // preparing query
        ps = conn.prepareStatement(QUERY_SELECT_TEACHER_ID);
        ps.setInt(1, id_teacher);
        rs = ps.executeQuery();
        if (rs.next()) {
          teacher = new Teacher(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4));
        }
        ps.close();
        rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
        System.out.println(e.getErrorCode() + " " + e.getMessage());
      } finally {
        // close db connection
        DAOFactoryMySql.closeDbConnection(conn);
      }
    }
    return teacher;
  }

  @Override
  public Teacher selectTeacher(String surname, String name) {
    PreparedStatement ps;

    ResultSet rs;

    Teacher teacher = null;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        ps = conn.prepareStatement(QUERY_SELECT_TEACHER_SURNAME_NAME);
        ps.setString(1, surname);
        ps.setString(2, name);
        rs = ps.executeQuery();
        if (rs.next()) {
          teacher = new Teacher(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4));
        }
      } catch (SQLException e) {
        e.printStackTrace();
        System.out.println(e.getErrorCode() + " " + e.getMessage());
      } finally {
        DAOFactoryMySql.closeDbConnection(conn);
      }
    }
    return teacher;
  }

  @Override
  public boolean teacherExist(String surname, String name) {
    PreparedStatement ps;

    boolean res = false;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        ps = conn.prepareStatement(QUERY_SELECT_TEACHER_SURNAME_NAME);
        ps.setString(1, surname);
        ps.setString(2, name);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) res = true;
      } catch (SQLException e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
      } finally {
        DAOFactoryMySql.closeDbConnection(conn);
      }
    }

    return res;
  }

  @Override
  public boolean activateTeacher(int id_teacher) {
    return alterActiveStatus(id_teacher, QUERY_UPDATE_ACTIVE_ID, SET_ACTIVE);
  }

  @Override
  public boolean deleteTeacher(int id_teacher) {
    return alterActiveStatus(id_teacher, QUERY_UPDATE_ACTIVE_ID, SET_DISABLED);
  }

  private boolean alterActiveStatus(int id_teacher, String query_to_alter, boolean activate) {
    PreparedStatement ps;

    boolean res = false;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        ps = conn.prepareStatement(query_to_alter);
        ps.setBoolean(1, activate);
        ps.setInt(2, id_teacher);
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
  public List<Teacher> selectAllTeachers() {
    Statement st;
    ResultSet rs;
    List<Teacher> teachers = null;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try { // preparing query
        st = conn.createStatement();
        rs = st.executeQuery(QUERY_SELECT_ALL_TEACHER);
        teachers = new ArrayList<>();
        while (rs.next()) {
          Teacher teacher = new Teacher(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4));
          teachers.add(teacher);
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
    return teachers;
  }

  @Override
  public List<Teacher> selectAllActiveTeachers() {
    Statement st;
    ResultSet rs;
    List<Teacher> teachers = null;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try { // preparing query
        st = conn.createStatement();
        rs = st.executeQuery(QUERY_SELECT_ALL_ACTIVE_TEACHER);
        teachers = new ArrayList<>();
        while (rs.next()) {
          Teacher teacher = new Teacher(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4));
          teachers.add(teacher);
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
    return teachers;
  }
}
