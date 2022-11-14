package com.tweb.dpd.learn4glory.dao;

import com.tweb.dpd.learn4glory.model.Course;

import java.util.List;

public interface CourseDAO {
  /*
  insert
   */
  int insertCourse(Course course);
  int insertCourse(String title);

  /*
  select
   */
  Course selectCourse(int id_course);
  Course selectCourse(String title);

  /*
  activate/disable course
   */
  boolean activateCourse(int id_course);
  boolean activateCourse(String title);
  boolean deleteCourse(int id_course);
  boolean deleteCourse(String title);

  /*
  retrieve all courses
   */
  List<Course> selectAllCourses();
  List<Course> selectAllAvailableCourses();

}
