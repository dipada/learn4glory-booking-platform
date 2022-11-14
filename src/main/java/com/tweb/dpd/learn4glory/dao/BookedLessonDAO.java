package com.tweb.dpd.learn4glory.dao;

import com.tweb.dpd.learn4glory.model.BookedLesson;
import com.tweb.dpd.learn4glory.model.Course;
import com.tweb.dpd.learn4glory.model.Lesson;
import com.tweb.dpd.learn4glory.model.Teacher;

import java.util.List;

public interface BookedLessonDAO {
  //int insertBookedLesson(BookedLesson bookedLesson);
  int insertBookedLesson(Lesson lesson);

  BookedLesson selectBookedLessonById(int id_bookedLesson);

  boolean markLessonCompleted(int id_bookedLesson);

  boolean markLessonDeleted(int id_bookedLesson);

  List<BookedLesson> selectAllBookedLessons();
  List<BookedLesson> selectAllBookedLessonsOfUser(int id_user);
  List<BookedLesson> selectAllActiveBookedLessons(); // only administrator
}
