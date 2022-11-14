package com.tweb.dpd.learn4glory.dao;

import com.tweb.dpd.learn4glory.model.User;

import java.util.List;

public interface UserDAO {

  int insertUser(User user);

  User selectUser(int id_user);

  User selectUser(String email);

  boolean emailRegistered(String email);

  boolean usernameRegistered(String username);

  User selectUserByEmailPassword(String email, String password);

  List<User> selectAllUsers();

  boolean cancelUserById(int id);

  boolean activateUserById(int id);
}
