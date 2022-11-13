package com.tweb.dpd.learn4glory.model;

/**
 * This class represents a booked lesson
 */
public class BookedLesson {

  private int id_booking;
  private int user;
  private int course;
  private int teacher;
  private WEEK_DAY week_day;
  private int hour;
  private boolean completed;
  private boolean deleted;


  public BookedLesson(int id_booking, int user, int course, int teacher, WEEK_DAY week_day, int hour, boolean completed, boolean deleted) {
    this.id_booking = id_booking;
    this.user = user;
    this.course = course;
    this.teacher = teacher;
    this.week_day = week_day;
    this.hour = hour;
    this.completed = completed;
    this.deleted = deleted;
  }

  public BookedLesson(int user, int course, int teacher, WEEK_DAY week_day, int hour, boolean completed, boolean deleted) {
    this(-1, user, course, teacher, week_day, hour, completed, deleted);
  }

  public int getId_booking() {
    return id_booking;
  }

  public void setId_booking(int id_booking) {
    this.id_booking = id_booking;
  }

  public int getUser() {
    return user;
  }

  public void setUser(int user) {
    this.user = user;
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

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }
}
