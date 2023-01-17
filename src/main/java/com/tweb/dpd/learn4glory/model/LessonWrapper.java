package com.tweb.dpd.learn4glory.model;

/**
 * Wrapper class of Lesson class
 * Used for display all information about teacher and course of a Lesson
 */

public class LessonWrapper {
  private int id_lesson;
  private Course course;
  private Teacher teacher;
  private WEEK_DAY week_day;
  private int hour;
  private boolean active;

  public LessonWrapper(int id_lesson, Course course, Teacher teacher, WEEK_DAY week_day, int hour, boolean active) {
    this.id_lesson = id_lesson;
    this.course = course;
    this.teacher = teacher;
    this.week_day = week_day;
    this.hour = hour;
    this.active = active;
  }

  @Override
  public String toString() {
    return "LessonWrapper{" +
            "id_lesson=" + id_lesson +
            ", course=" + course +
            ", teacher=" + teacher +
            ", week_day=" + week_day +
            ", hour=" + hour +
            ", active=" + active +
            '}';
  }
}
