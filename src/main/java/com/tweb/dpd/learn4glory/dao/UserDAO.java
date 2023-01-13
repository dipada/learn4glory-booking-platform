package com.tweb.dpd.learn4glory.dao;

import com.tweb.dpd.learn4glory.model.User;

import java.util.List;

public interface UserDAO {

  int insertUser(User user);

  int inserUser(String username, String email, String password);

  User selectUser(int id_user);
  User selectUser(String email);
  String selectUserPassword(int id_user);
  String selectUserPassword(String email);
  boolean emailExist(String email);
  boolean usernameExist(String username);
  List<User> selectAllUsers();
  List<User> selectAllActiveUsers();
  boolean activateUser(int id_user);
  boolean activateUser(String email);
  boolean deleteUser(int id_user);
  boolean deleteUser(String email);
}
