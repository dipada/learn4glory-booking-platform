package com.tweb.dpd.learn4glory.model;

/**
 * Wrapper class of BookedLesson class
 * Used for display all information about Lessons and booking
 */

public class BookedLessonWrapper {
  private int id_booking;
  private User user;
  private LessonWrapper lesson;
  private WEEK_DAY week_day;
  private int hour;
  private boolean completed;
  private boolean deleted;

  public BookedLessonWrapper(int id_booking, User user, LessonWrapper lesson, WEEK_DAY week_day, int hour, boolean completed, boolean deleted) {
    this.id_booking = id_booking;
    this.user = user;
    this.lesson = lesson;
    this.week_day = week_day;
    this.hour = hour;
    this.completed = completed;
    this.deleted = deleted;
  }

  @Override
  public String toString() {
    return "BookedLessonWrapper{" + "id_booking=" + id_booking + ", user=" + user + ", lesson=" + lesson + ", week_day=" + week_day + ", hour=" + hour + ", completed=" + completed + ", deleted=" + deleted + '}';
  }
}
