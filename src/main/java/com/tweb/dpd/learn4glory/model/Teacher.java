package com.tweb.dpd.learn4glory.model;

public class Teacher {
  private int id_teacher;
  private String surname;
  private String name;
  private boolean active;

  public Teacher(int id_teacher, String surname, String name, boolean active) {
    this.id_teacher = id_teacher;
    this.surname = surname;
    this.name = name;
    this.active = active;
  }

  public int getId_teacher() {
    return id_teacher;
  }

  public void setId_teacher(int id_teacher) {
    this.id_teacher = id_teacher;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}
