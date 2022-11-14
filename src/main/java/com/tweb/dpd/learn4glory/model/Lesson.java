package com.tweb.dpd.learn4glory.model;

/**
 * This class represents single lesson
 * held by a teacher
 */
public class Lesson {
  private int id_lesson;
  private int course;
  private int teacher;
  private WEEK_DAY week_day;
  private int hour;

  private boolean active;

  public Lesson(int id_lesson, int course, int teacher, WEEK_DAY week_day, int hour, boolean active) {
    this.id_lesson = id_lesson;
    this.course = course;
    this.teacher = teacher;
    this.week_day = week_day;
    this.hour = hour;
    this.active = active;
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

  public WEEK_DAY getWeek_day() {
    return week_day;
  }

  public void setWeek_day(WEEK_DAY week_day) {
    this.week_day = week_day;
  }

  public int getHour() {
    return hour;
  }

  public void setHour(int hour) {
    this.hour = hour;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}
