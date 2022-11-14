package com.tweb.dpd.learn4glory.dao.mysql;

import com.tweb.dpd.learn4glory.dao.LessonDAO;
import com.tweb.dpd.learn4glory.model.Course;
import com.tweb.dpd.learn4glory.model.Lesson;
import com.tweb.dpd.learn4glory.model.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LessonDAOImplMySql implements LessonDAO {

  private final String QUERY_INSERT_LESSON = "INSERT INTO LESSON(COURSE, TEACHER,ACTIVE) VALUES (?,?,?)";
  private final String QUERY_SELECT_BY_ID = "SELECT * FROM LESSON WHERE ID_LESSON=?";
  private final String QUERY_SELECT_BY_COURSE_TEACHER_IDS = "SELECT * FROM LESSON WHERE COURSE=? AND TEACHER=?";
  private final String QUERY_SELECT_ALL_LESSONS = "SELECT * FROM LESSON";
  private final String QUERY_SELECT_ALL_ACTIVE_LESSON = "SELECT * FROM LESSON WHERE active=true";
  private final String QUERY_UPDATE_ACTIVE_ID = "UPDATE LESSON SET active=? WHERE ID_LESSON=?";
  private final String QUERY_UPDATE_ACTIVE_BY_COURSE_TEACHER = "UPDATE LESSON SET active=? WHERE COURSE=? AND TEACHER=?";
  private final boolean SET_ACTIVE = true;
  private final boolean SET_DISABLED = false;

  /**
   * Insert given lesson into db.
   * The teacher and course of lesson have to exist.
   *
   * @param lesson lesson that want insert
   * @return the new lesson id if successfull, -1 otherwise.
   */
  @Override
  public int insertLesson(Lesson lesson) {

    PreparedStatement ps;
    ResultSet rs;
    int result = -1;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        ps = conn.prepareStatement(QUERY_INSERT_LESSON, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, lesson.getCourse());
        ps.setInt(2, lesson.getTeacher());
        ps.setBoolean(3, lesson.isActive());
        ps.executeUpdate();
        rs = ps.getGeneratedKeys();
        if (rs.next()) {
          result = rs.getInt(1);
        }
      } catch (SQLException e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
      } finally {
        DAOFactoryMySql.closeDbConnection(conn);
      }
    }
    return result;

  }

  @Override
  public int insertLesson(Course course, Teacher teacher) {
    PreparedStatement ps;
    ResultSet rs;
    int result = -1;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        ps = conn.prepareStatement(QUERY_INSERT_LESSON, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, course.getId_course());
        ps.setInt(2, teacher.getId_teacher());
        ps.setBoolean(3, SET_ACTIVE);
        ps.executeUpdate();
        rs = ps.getGeneratedKeys();
        if (rs.next()) {
          result = rs.getInt(1);
        }
        rs.close();
        ps.close();
      } catch (SQLException e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
      } finally {
        DAOFactoryMySql.closeDbConnection(conn);
      }
    }
    return result;
  }

  @Override
  public Lesson selectLesson(int id_lesson) {
    PreparedStatement ps;

    ResultSet rs;

    Lesson lesson = null;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        ps = conn.prepareStatement(QUERY_SELECT_BY_ID);
        ps.setInt(1, id_lesson);
        rs = ps.executeQuery();
        if (rs.next()) {
          lesson = new Lesson(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getBoolean(4));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        DAOFactoryMySql.closeDbConnection(conn);
      }
    }
    return lesson;

  }

  @Override
  public Lesson selectLesson(int id_course, int id_teacher) {
    PreparedStatement ps;

    ResultSet rs;

    Lesson lesson = null;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        ps = conn.prepareStatement(QUERY_SELECT_BY_COURSE_TEACHER_IDS);
        ps.setInt(1, id_course);
        ps.setInt(2, id_teacher);
        rs = ps.executeQuery();
        if (rs.next()) {
          lesson = new Lesson(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getBoolean(4));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        DAOFactoryMySql.closeDbConnection(conn);
      }
    }
    return lesson;
  }

  @Override
  public List<Lesson> selectAllLessons() {
    return getLessons(QUERY_SELECT_ALL_LESSONS);
  }

  @Override
  public List<Lesson> selectAllActiveLessons() {
    return getLessons(QUERY_SELECT_ALL_ACTIVE_LESSON);
  }

  private List<Lesson> getLessons(String query_select_all_active_lesson) {
    Statement st;
    ResultSet rs;

    List<Lesson> lessons = null;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        st = conn.createStatement();
        rs = st.executeQuery(query_select_all_active_lesson);
        lessons = new ArrayList<>();
        while (rs.next()) {
          Lesson lesson = new Lesson(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getBoolean(4));
          lessons.add(lesson);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        DAOFactoryMySql.closeDbConnection(conn);
      }
    }
    return lessons;
  }

  @Override
  public boolean activateLesson(int id_lesson) {
    return alterActiveStatus(id_lesson, QUERY_UPDATE_ACTIVE_ID, SET_ACTIVE);
  }

  @Override
  public boolean deleteLesson(int id_lesson) {
    return alterActiveStatus(id_lesson, QUERY_UPDATE_ACTIVE_ID, SET_DISABLED);
  }

  private boolean alterActiveStatus(int id_lesson, String query_to_alter, boolean active) {
    PreparedStatement ps;

    boolean res = false;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        ps = conn.prepareStatement(query_to_alter);
        ps.setBoolean(1, active);
        ps.setInt(2, id_lesson);
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
  public boolean activateLesson(int id_course, int id_teacher) {
    return alterActiveStatus(id_course, id_teacher, QUERY_UPDATE_ACTIVE_BY_COURSE_TEACHER, SET_ACTIVE);
  }

  @Override
  public boolean deleteLesson(int id_course, int id_teacher) {
    return alterActiveStatus(id_course, id_teacher, QUERY_UPDATE_ACTIVE_BY_COURSE_TEACHER, SET_DISABLED);
  }

  private boolean alterActiveStatus(int id_course, int id_teacher, String query_to_alter, boolean active) {
    PreparedStatement ps;

    boolean res = false;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        ps = conn.prepareStatement(query_to_alter);
        ps.setBoolean(1, active);
        ps.setInt(2, id_course);
        ps.setInt(3, id_teacher);
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

}
