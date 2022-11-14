package com.tweb.dpd.learn4glory.dao;

import com.tweb.dpd.learn4glory.dao.mysql.DbResponse;
import com.tweb.dpd.learn4glory.model.Course;

import java.util.List;

public interface CourseDAO {
  /*
  insert
   */
  boolean insertCourse(Course course);

  boolean insertCourse(String title);

  /*
  select
   */
  Course selectCourse(int id_course);

  Course selectCourse(String title);

  /*
  delete
   */
  boolean deleteCourse(int id_course);

  boolean deleteCourse(String title);

  /*
  activate/disable course
   */
  boolean activateCourse(int id_course);

  boolean activateCourse(String title);

  boolean disableCourse(int id_course);

  boolean disableCourse(String title);

  /*
  retrieve all courses
   */
  List<Course> selectAllCourses();

  List<Course> selectAllAvailableCourses();

}
