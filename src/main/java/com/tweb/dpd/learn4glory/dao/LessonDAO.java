package com.tweb.dpd.learn4glory.dao;

import com.tweb.dpd.learn4glory.model.Course;
import com.tweb.dpd.learn4glory.model.Lesson;
import com.tweb.dpd.learn4glory.model.Teacher;
import com.tweb.dpd.learn4glory.model.WEEK_DAY;

import java.util.List;

public interface LessonDAO {

  int insertLesson(Lesson lesson);

  int insertLesson(Course course, Teacher teacher, WEEK_DAY week_day, int hour);

  Lesson selectLesson(int id_lesson);

  Lesson selectLesson(int id_course, int id_teacher);

  List<Lesson> selectAllLessons();

  List<Lesson> selectAllActiveLessons();

  boolean activateLesson(int id_lesson);

  boolean activateLesson(int id_course, int id_teacher);

  boolean deleteLesson(int id_lesson);

  boolean deleteLesson(int id_course, int id_teacher);
}
