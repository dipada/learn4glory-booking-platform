package com.tweb.dpd.learn4glory.model;

public class User {
  private int id_user;
  private String username;
  private String password;
  private String email;
  private boolean admin;

  public User(int id_user, String username, String password, String email, boolean admin) {
    this.id_user = id_user;
    this.username = username;
    this.password = password;
    this.email = email;
    this.admin = admin;
  }

  public User(String username, String password, String email, boolean admin) {
    this(-1, username, password, email, admin);
  }

  public int getId_user() {
    return id_user;
  }

  public void setId_user(int id_user) {
    this.id_user = id_user;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(boolean admin) {
    this.admin = admin;
  }
}
