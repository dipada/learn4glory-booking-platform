package com.tweb.dpd.learn4glory.model;

/**
 * This class represents lesson held by a teacher
 */
public class Lesson {
  private int id_lesson;
  private int course;
  private int teacher;
  private boolean active;

  public Lesson(int id_lesson, int course, int teacher, boolean active) {
    this.id_lesson = id_lesson;
    this.course = course;
    this.teacher = teacher;
    this.active = active;
  }

  public Lesson(int course, int teacher, boolean active) {
    this(-1, course, teacher, active);
  }

  public Lesson(int course, int teacher) {
    this(-1, course, teacher, true);
  }

  public int getId_lesson() {
    return id_lesson;
  }

  public void setId_lesson(int id_lesson) {
    this.id_lesson = id_lesson;
  }

  public int getCourse() {
    return course;
  }

  public void setCourse(int course) {
    this.course = course;
  }

  public int getTeacher() {
    return teacher;
  }

  public void setTeacher(int teacher) {
    this.teacher = teacher;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public String toString() {
    return "Lesson{" + "id_lesson=" + id_lesson + ", course=" + course + ", teacher=" + teacher + ", active=" + active + '}';
  }
}
