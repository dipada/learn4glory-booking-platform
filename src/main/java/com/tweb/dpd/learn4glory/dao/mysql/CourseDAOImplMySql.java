package com.tweb.dpd.learn4glory.dao.mysql;

import com.mysql.jdbc.MysqlErrorNumbers;
import com.tweb.dpd.learn4glory.dao.CourseDAO;

import java.sql.*;

/**
 * Concrete CourseDAO implementation for mysql
 */

public class CourseDAOImplMySql implements CourseDAO {
  /*
  @Override
  public int insertCourse(String title) {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int result = -1;
    try{
      conn = DAOFactoryMySql.openConnectionToDb();
      ps = conn.prepareStatement("INSERT INTO course(title) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
      ps.setString(1,title);
      ps.executeUpdate();
      rs = ps.getGeneratedKeys();
      if (rs.next()) {
        result = rs.getInt(1);
      }
    } catch (SQLException e) {
      System.out.println(e.getSQLState());
      System.out.println(e.getMessage());
      e.printStackTrace();
    } finally {
      DAOFactoryMySql.closeDbConnection(conn);
    }
    return result;
  }

 */
}
