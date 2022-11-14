package com.tweb.dpd.learn4glory.dao;

import com.tweb.dpd.learn4glory.model.Lesson;
import com.tweb.dpd.learn4glory.model.WEEK_DAY;

import java.util.List;

public interface LessonDAO {

  int insertLesson(Lesson lesson);

  Lesson selectLesson(int id_lesson);
  Lesson selectLesson(String teacher, WEEK_DAY week_day, int hour);

  boolean isAvailable(int id_lesson);

  boolean isAvailable(int id_teacher, WEEK_DAY week_day, int hour);

  boolean activateLesson(int id_lesson);

  // when a specific lesson is booked or teacher is no longer available
  boolean deleteLesson(int id_lesson);

  boolean deleteLesson(int id_teacher, WEEK_DAY week_day, int hour); // a teacher can hold only 1 type lesson in a specific hour


  List<Lesson> selectAllLessons();
  List<Lesson> selectAllAvailableLessons();
}
