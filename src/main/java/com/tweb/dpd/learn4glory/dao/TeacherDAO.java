package com.tweb.dpd.learn4glory.dao;

import com.tweb.dpd.learn4glory.model.Teacher;

import java.util.List;

public interface TeacherDAO {
  int insertTeacher(String name, String surname);

  int insertTeacher(Teacher teacher);

  List<Teacher> selectTeachersByCourseId(int course_id);

  Teacher selectTeacher(int id_teacher);

  Teacher selectTeacher(String surname, String name);

  /*
  assuming that can be only 1 teacher with a couple of name surname
  for extend this, add ID_number to db and then that param here
  */
  boolean teacherExist(String surname, String name);

  boolean activateTeacher(int id_teacher);

  boolean deleteTeacher(int id_teacher);

  List<Teacher> selectAllTeachers();

  List<Teacher> selectAllActiveTeachers();

}
