package com.tweb.dpd.learn4glory.model;

/**
 * This class represents a booked lesson
 */
public class BookedLesson {

  private int id_booking;
  private int user;
  private int lesson;
  private boolean completed;
  private boolean deleted;

  public BookedLesson(int id_booking, int user, int lesson, boolean completed, boolean deleted) {
    this.id_booking = id_booking;
    this.user = user;
    this.lesson = lesson;
    this.completed = completed;
    this.deleted = deleted;
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

  public int getLesson() {
    return lesson;
  }

  public void setLesson(int lesson) {
    this.lesson = lesson;
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
