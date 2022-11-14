package com.tweb.dpd.learn4glory.dao.mysql;

import com.tweb.dpd.learn4glory.dao.UserDAO;
import com.tweb.dpd.learn4glory.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Concrete UserDAO implementation for mysql
 */
public class UserDAOImplMySql implements UserDAO {

  private final String QUERY_INSERT_USER = "INSERT INTO USER(USERNAME,PASSWORD,EMAIL,ADMIN) VALUES (?,?,?,?)";
  private final String QUERY_SELECT_BY_ID = "SELECT * FROM USER WHERE ID_USER=?";
  private final String QUERY_SELECT_BY_EMAIL = "SELECT * FROM USER WHERE EMAIL=?";
  private final String QUERY_SELECT_USER_PASSWORD_BY_ID = "SELECT PASSWORD FROM USER WHERE ID_USER=?";
  private final String QUERY_SELECT_USER_PASSWORD_BY_EMAIL = "SELECT PASSWORD FROM USER WHERE EMAIL=?";
  private final String QUERY_SELECT_EMAIL = "SELECT EMAIL FROM USER WHERE EMAIL=?";
  private final String QUERY_SELECT_USERNAME = "SELECT USERNAME FROM USER WHERE USERNAME=?";
  private final String QUERY_SELECT_ALL_USERS = "SELECT * FROM USER";
  private final String QUERY_SELECT_ALL_ACTIVE_USER = "SELECT * FROM USER WHERE ACTIVE=TRUE";
  private final String QUERY_UPDATE_ACTIVE_ID = "UPDATE USER SET active=? WHERE ID_USER=?";
  private final String QUERY_UPDATE_ACTIVE_EMAIL = "UPDATE USER SET active=? WHERE EMAIL=?";
  private final boolean SET_ACTIVE = true;
  private final boolean SET_DISABLED = false;


  @Override
  public int insertUser(User user) {
    PreparedStatement st;
    ResultSet rs;
    int result = -1;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try { // preparing query
        st = conn.prepareStatement(QUERY_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
        st.setString(1, user.getUsername());
        st.setString(2, user.getPassword());
        st.setString(3, user.getEmail());
        st.setBoolean(4, user.isAdmin());
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
  public User selectUser(int id_user) {
    PreparedStatement ps;

    ResultSet rs;

    User user = null;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        ps = conn.prepareStatement(QUERY_SELECT_BY_ID);
        ps.setInt(1, id_user);
        rs = ps.executeQuery();
        if (rs.next()) {
          user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getBoolean(6));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        DAOFactoryMySql.closeDbConnection(conn);
      }
    }
    return user;
  }

  @Override
  public User selectUser(String email) {
    PreparedStatement ps;

    ResultSet rs;

    User user = null;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        ps = conn.prepareStatement(QUERY_SELECT_BY_EMAIL);
        ps.setString(1, email.toLowerCase());
        rs = ps.executeQuery();
        if (rs.next()) {
          user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getBoolean(6));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        DAOFactoryMySql.closeDbConnection(conn);
      }
    }
    return user;
  }

  @Override
  public String selectUserPassword(int id_user) {
    PreparedStatement ps;

    ResultSet rs;

    String res = null;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        ps = conn.prepareStatement(QUERY_SELECT_USER_PASSWORD_BY_ID);
        ps.setInt(1, id_user);
        rs = ps.executeQuery();
        if (rs.next()) {
          res = rs.getString(1);
        }
        rs.close();
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
  public String selectUserPassword(String email) {
    PreparedStatement ps;

    ResultSet rs;

    String res = null;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        ps = conn.prepareStatement(QUERY_SELECT_USER_PASSWORD_BY_EMAIL);
        ps.setString(1, email);
        rs = ps.executeQuery();
        if (rs.next()) {
          res = rs.getString(1);
        }
        rs.close();
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
  public boolean emailExist(String email) {
    return selectColumn(email, QUERY_SELECT_EMAIL);
  }

  @Override
  public boolean usernameExist(String username) {
    return selectColumn(username, QUERY_SELECT_USERNAME);
  }

  private boolean selectColumn(String match_value, String query_to_execute) {
    PreparedStatement ps;

    ResultSet rs;

    boolean exist = false;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        ps = conn.prepareStatement(query_to_execute);
        ps.setString(1, match_value);
        rs = ps.executeQuery();
        if (rs.next()) {
          exist = true;
        }
        rs.close();
        ps.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        DAOFactoryMySql.closeDbConnection(conn);
      }
    }
    return exist;
  }

  @Override
  public List<User> selectAllUsers() {
    PreparedStatement ps;
    ResultSet rs;
    List<User> users = null;

    Connection conn = DAOFactoryMySql.openConnectionToDb();
    if (conn != null) {
      try {
        ps = conn.prepareStatement(QUERY_SELECT_ALL_USERS);
        rs = ps.executeQuery();
        users = new ArrayList<>();
        while (rs.next()) {
          User c = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getBoolean(6));
          users.add(c);
        }
      } catch (SQLException e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
      } finally {
        DAOFactoryMySql.closeDbConnection(conn);
      }
    }
    return users;
  }

  @Override
  public List<User> selectAllActiveUsers() {
    PreparedStatement ps;
    ResultSet rs;
    List<User> users = null;

    Connection conn = DAOFactoryMySql.openConnectionToDb();
    if (conn != null) {
      try {
        ps = conn.prepareStatement(QUERY_SELECT_ALL_ACTIVE_USER);
        rs = ps.executeQuery();
        users = new ArrayList<>();
        while (rs.next()) {
          User c = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getBoolean(6));
          users.add(c);
        }
      } catch (SQLException e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
      } finally {
        DAOFactoryMySql.closeDbConnection(conn);
      }
    }
    return users;
  }

  @Override
  public boolean activateUser(int id_user) {
    return alterActiveStatus(id_user, QUERY_UPDATE_ACTIVE_ID, SET_ACTIVE);
  }

  @Override
  public boolean activateUser(String email) {
    return alterActiveStatus(email, QUERY_UPDATE_ACTIVE_EMAIL, SET_ACTIVE);
  }

  @Override
  public boolean deleteUser(int id_user) {
    return alterActiveStatus(id_user, QUERY_UPDATE_ACTIVE_ID, SET_DISABLED);
  }

  @Override
  public boolean deleteUser(String email) {
    return alterActiveStatus(email, QUERY_UPDATE_ACTIVE_EMAIL, SET_DISABLED);
  }

  private boolean alterActiveStatus(int id_user, String query_to_alter, boolean activate) {
    PreparedStatement ps;

    boolean res = false;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        ps = conn.prepareStatement(query_to_alter);
        ps.setBoolean(1, activate);
        ps.setInt(2, id_user);
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

  private boolean alterActiveStatus(String email, String query_to_alter, boolean activate) {
    PreparedStatement ps;

    boolean res = false;

    Connection conn = DAOFactoryMySql.openConnectionToDb();

    if (conn != null) {
      try {
        ps = conn.prepareStatement(query_to_alter);
        ps.setBoolean(1, activate);
        ps.setString(2, email);
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


