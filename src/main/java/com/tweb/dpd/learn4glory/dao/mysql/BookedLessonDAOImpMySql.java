package com.tweb.dpd.learn4glory.dao.mysql;

import com.tweb.dpd.learn4glory.dao.BookedLessonDAO;
import com.tweb.dpd.learn4glory.model.BookedLesson;
import com.tweb.dpd.learn4glory.model.Lesson;
import com.tweb.dpd.learn4glory.model.User;
import com.tweb.dpd.learn4glory.model.WEEK_DAY;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookedLessonDAOImpMySql implements BookedLessonDAO {

  private final String QUERY_INSERT_BOOKEDLESSON = "INSERT INTO BOOKED(USER, LESSON, WEEK_DAY, HOUR, COMPLETED, DELETED) VALUES (?,?,?,?,?,?)";
  private final String QUERY_UPDATE_COMPLETED = "UPDATE BOOKED SET COMPLETED=TRUE WHERE ID_BOOKING=?";
  private final String QUERY_UPDATE_DELETED = "UPDATE BOOKED SET DELETED=TRUE WHERE ID_BOOKING=?";
  private final String QUERY_SELECT_BOOKEDLESSON_BYID = "SELECT * FROM BOOKED WHERE ID_BOOKING=?";
  private final String QUERY_SELECT_ALL_BOOKEDLESSON = "SELECT * FROM BOOKED";
  private final String QUERY_SELECT_ALL_BOOKEDLESSON_USERID = "SELECT * FROM BOOKED WHERE USER=?";
  private final String QUERY_SELECT_ALL_ACTIVE_BOOKEDLESSON = "SELECT * FROM BOOKED WHERE COMPLETED=FALSE AND DELETED=FALSE";
  private final String QUERY_DELETE_BOOKEDLESSON = "UPDATE BOOKED SET deleted=? WHERE ID_BOOKING=?";
  @Override
  public int insertBookedLesson(BookedLesson bookedLesson) {
    PreparedStatement ps;
    ResultSet rs;
    Connection conn = DAOFactoryMySql.openConnectionToDb();
    int result = -1;

    if (conn != null) {
      try {
        ps = conn.prepareStatement(QUERY_INSERT_BOOKEDLESSON, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, bookedLesson.getUser());
        ps.setInt(2, bookedLesson.getLesson());
        ps.setString(3, bookedLesson.getWeek_day().toString());
        ps.setString(4, String.valueOf(bookedLesson.getHour()));
        ps.setBoolean(5, bookedLesson.isCompleted());
        ps.setBoolean(6, bookedLesson.isDeleted());
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
  public int insertBookedLesson(User user, Lesson lesson){
    return insertBookedLesson(new BookedLesson(user.getId_user(), lesson.getId_lesson(), lesson.getWeek_day(), lesson.getHour(), false, false));
  }

  @Override
  public BookedLesson selectBookedLessonById(int id_bookedLesson) {
    PreparedStatement ps;
    ResultSet rs;
    BookedLesson bookedLesson = null;
    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        ps = conn.prepareStatement(QUERY_SELECT_BOOKEDLESSON_BYID);
        ps.setInt(1,id_bookedLesson);
        rs = ps.executeQuery();
        while (rs.next()) {
          bookedLesson = new BookedLesson(rs.getInt(1), rs.getInt(2), rs.getInt(3), WEEK_DAY.valueOf(rs.getString(4)), Integer.parseInt(rs.getString(5)), rs.getBoolean(6), rs.getBoolean(7));
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
    return bookedLesson;
  }

  @Override
  public boolean markLessonCompleted(int id_bookedLesson) {
    PreparedStatement ps;
    boolean res = false;
    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        ps = conn.prepareStatement(QUERY_UPDATE_COMPLETED, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, id_bookedLesson);
        res = ps.executeUpdate() > 0;
        ps.close();
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
  public boolean markLessonDeleted(int id_bookedLesson) {
    PreparedStatement ps;
    boolean res = false;
    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        ps = conn.prepareStatement(QUERY_UPDATE_DELETED, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, id_bookedLesson);
        res = ps.executeUpdate() > 0;
        ps.close();
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
  public List<BookedLesson> selectAllBookedLessons() {
    Statement st;
    ResultSet rs;
    List<BookedLesson> bookedLessons = null;
    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        st = conn.createStatement();
        rs = st.executeQuery(QUERY_SELECT_ALL_BOOKEDLESSON);
        bookedLessons = new ArrayList<>();
        while (rs.next()) {
          BookedLesson bookedLesson = new BookedLesson(rs.getInt(1), rs.getInt(2), rs.getInt(3), WEEK_DAY.valueOf(rs.getString(4)), Integer.parseInt(rs.getString(5)), rs.getBoolean(6), rs.getBoolean(7));
          bookedLessons.add(bookedLesson);
        }
      } catch (SQLException e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
      } finally {
        DAOFactoryMySql.closeDbConnection(conn);
      }
    }
    return bookedLessons;
  }

  @Override
  public List<BookedLesson> selectAllBookedLessonsOfUser(int id_user) {
    PreparedStatement ps;
    ResultSet rs;
    List<BookedLesson> bookedLessons = null;
    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        ps = conn.prepareStatement(QUERY_SELECT_ALL_BOOKEDLESSON_USERID);
        ps.setInt(1, id_user);
        rs = ps.executeQuery();
        bookedLessons = new ArrayList<>();
        while (rs.next()) {
          BookedLesson bookedLesson = new BookedLesson(rs.getInt(1), rs.getInt(2), rs.getInt(3), WEEK_DAY.valueOf(rs.getString(4)), Integer.parseInt(rs.getString(5)), rs.getBoolean(6), rs.getBoolean(7));
          bookedLessons.add(bookedLesson);
        }
      } catch (SQLException e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
      } finally {
        DAOFactoryMySql.closeDbConnection(conn);
      }
    }
    return bookedLessons;
  }

  @Override
  public List<BookedLesson> selectAllActiveBookedLessons() {
    Statement st;
    ResultSet rs;
    List<BookedLesson> bookedLessons = null;
    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        st = conn.createStatement();
        rs = st.executeQuery(QUERY_SELECT_ALL_ACTIVE_BOOKEDLESSON);
        bookedLessons = new ArrayList<>();
        while (rs.next()) {
          BookedLesson bookedLesson = new BookedLesson(rs.getInt(1), rs.getInt(2), rs.getInt(3), WEEK_DAY.valueOf(rs.getString(4)), Integer.parseInt(rs.getString(5)), rs.getBoolean(6), rs.getBoolean(7));
          bookedLessons.add(bookedLesson);
        }
      } catch (SQLException e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
      } finally {
        DAOFactoryMySql.closeDbConnection(conn);
      }
    }
    return bookedLessons;
  }

  @Override
  public boolean deleteBookedLesson(int id_bookedLesson) {
    PreparedStatement ps;
    boolean res = false;
    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        ps = conn.prepareStatement(QUERY_DELETE_BOOKEDLESSON);
        ps.setBoolean(1, true);
        ps.setInt(2,id_bookedLesson);
        res = ps.executeUpdate() > 0;
        ps.close();
      } catch (SQLException e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
      } finally {
        DAOFactoryMySql.closeDbConnection(conn);
      }
    }
    return res;
  }
}
