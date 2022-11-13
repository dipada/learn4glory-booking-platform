package com.tweb.dpd.learn4glory.model;

/**
 * This class represents a course
 *
 */
public class Course {
  private int id_course;
  private String title;
  private boolean active;

  public Course(int id_course, String title, boolean active){
    this.id_course = id_course;
    this.title = title;
    this.active = active;
  }

  public int getId_course() {
    return id_course;
  }

  public void setId_course(int id_course) {
    this.id_course = id_course;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}
