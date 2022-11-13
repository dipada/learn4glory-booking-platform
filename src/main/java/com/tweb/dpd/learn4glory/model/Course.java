package com.tweb.dpd.learn4glory.model;

/**
 * This class represents a course
 *
 */
public class Course {
  private int id;
  private String title;
  private boolean active;

  public Course(int id_course, String title, boolean active){
    id = id_course;
    this.title = title;
    this.active = active;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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
