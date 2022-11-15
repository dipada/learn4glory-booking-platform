package com.tweb.dpd.learn4glory.controller;

import com.tweb.dpd.learn4glory.dao.DAOFactory;
import com.tweb.dpd.learn4glory.dao.mysql.CourseDAOImplMySql;
import com.tweb.dpd.learn4glory.dao.mysql.DAOFactoryMySql;
import com.tweb.dpd.learn4glory.model.Course;
import com.tweb.dpd.learn4glory.model.Lesson;
import com.tweb.dpd.learn4glory.model.Teacher;
import com.tweb.dpd.learn4glory.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.SortedMap;

@WebServlet(name = "Controller", value = "/Controller", loadOnStartup = 1, asyncSupported = true)
public class Controller extends HttpServlet {

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    ServletContext servletContext = config.getServletContext();
    String url = servletContext.getInitParameter("DB-URL");
    String user = servletContext.getInitParameter("user");
    String pwd = servletContext.getInitParameter("pwd");

    //DAOFactory daoFactory = new DAOFactoryMySql(url, user, pwd);
    DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL, new String[]{url, user, pwd});
    servletContext.setAttribute("daoFactory", daoFactory);

    /*
    System.out.println(daoFactory.getCourseDAO().selectCourse("matematica"));
    System.out.println(daoFactory.getCourseDAO().selectCourse(3));
    System.out.println(daoFactory.getCourseDAO().selectCourse("TWEB"));
    System.out.println(daoFactory.getCourseDAO().deleteCourse(15));
    System.out.println(daoFactory.getCourseDAO().activateCourse("corsoattivo"));

    System.out.println(daoFactory.getCourseDAO().deleteCourse("cc1"));
    System.out.println(daoFactory.getCourseDAO().deleteCourse(19));
    System.out.println(daoFactory.getCourseDAO().activateCourse("cc3"));
    System.out.println(daoFactory.getCourseDAO().activateCourse(21));

    Course course = new Course(-1, "mioo", true);
    System.out.println(course.getId_course());
    System.out.println(daoFactory.getCourseDAO().insertCourse(course));
    System.out.println(course.getId_course());

     */

    //System.out.println(daoFactory.getCourseDAO().selectAllCourses().size());

    //System.out.println(daoFactory.getCourseDAO().selectAllAvailableCourses());
/*
    User user1 = new User(-1, "prr", "asasasas", "asddasdasd@gmail.com", true, true);
    daoFactory.getUserDAO().insertUser(user1);
    System.out.println(daoFactory.getUserDAO().selectUser(1));
    System.out.println(daoFactory.getUserDAO().selectUser(6));
    System.out.println(daoFactory.getUserDAO().selectUser("admin@gmail.com"));
    System.out.println("kkkkk");
    System.out.println(daoFactory.getUserDAO().selectUserPassword("admin@gmail.com"));
    System.out.println(daoFactory.getUserDAO().selectUserPassword(6));
    System.out.println(daoFactory.getUserDAO().emailExist("admin@gmail.com"));
    System.out.println(daoFactory.getUserDAO().activateUser("aaa121"));
    System.out.println(daoFactory.getUserDAO().usernameExist("aaa121"));
    System.out.println(daoFactory.getUserDAO().usernameExist("user1"));
    System.out.println(daoFactory.getUserDAO().activateUser(1));
    System.out.println("asssssssss");
    System.out.println(daoFactory.getUserDAO().activateUser("admin@gmail.com"));
    System.out.println(daoFactory.getUserDAO().activateUser(11));
    System.out.println(daoFactory.getUserDAO().selectAllActiveUsers());
    System.out.println(daoFactory.getUserDAO().deleteUser(11));
    System.out.println(daoFactory.getUserDAO().deleteUser("admin@gmail.com"));
    System.out.println(daoFactory.getUserDAO().selectAllUsers());
    */

    /*
    System.out.println("oooooooooooooo");
    System.out.println(daoFactory.getTeacherDAO().selectAllTeachers());
    System.out.println("attivi ");
    System.out.println(daoFactory.getTeacherDAO().selectAllActiveTeachers());
    Teacher teacher = new Teacher(-1, "daaaaaaaaaa", "aaaa", true);
    System.out.println(daoFactory.getTeacherDAO().insertTeacher(teacher));
    System.out.println(daoFactory.getTeacherDAO().selectAllActiveTeachers());
    System.out.println(daoFactory.getTeacherDAO().teacherExist(teacher.getSurname(), teacher.getName()));
    System.out.println(daoFactory.getTeacherDAO().teacherExist("pingo", "pallo"));
    System.out.println(daoFactory.getTeacherDAO().activateTeacher(1));
    System.out.println(daoFactory.getTeacherDAO().selectAllActiveTeachers());
    System.out.println(daoFactory.getTeacherDAO().deleteTeacher(1));
    System.out.println("GENAAAAAAAAAAAAA");
    System.out.println(daoFactory.getTeacherDAO().selectTeacher(4));
    System.out.println("aaaaaaaaaaaaaaaaaaaaaa");
    System.out.println(daoFactory.getTeacherDAO().selectTeacher("gena","cristina"));
    System.out.println("afdsafdadfsdfasasdfsdf");
    System.out.println(daoFactory.getTeacherDAO().selectAllTeachers());
    System.out.println("sdasadsas");
    System.out.println(daoFactory.getTeacherDAO().activateTeacher(1));
    System.out.println(daoFactory.getTeacherDAO().activateTeacher(3));
    System.out.println(daoFactory.getTeacherDAO().selectAllActiveTeachers());
    */

    /*
    System.out.println("Tutte le lezioni");
    System.out.println(daoFactory.getLessonDAO().selectAllLessons());

    System.out.println("inserimento valido ");
    System.out.println(daoFactory.getLessonDAO().insertLesson(daoFactory.getCourseDAO().selectCourse(1), daoFactory.getTeacherDAO().selectTeacher(1)));

    System.out.println(daoFactory.getLessonDAO().insertLesson(daoFactory.getCourseDAO().selectCourse(2), daoFactory.getTeacherDAO().selectTeacher(1)));
    System.out.println(daoFactory.getLessonDAO().insertLesson(daoFactory.getCourseDAO().selectCourse(1), daoFactory.getTeacherDAO().selectTeacher(2)));


     */

/*
    System.out.println("Tutte le lezioni");
    System.out.println(daoFactory.getLessonDAO().selectAllLessons());
    System.out.println("Tutte le lezioni attive");
    System.out.println(daoFactory.getLessonDAO().selectAllActiveLessons());

    System.out.println("attivo 2 lezioni");
    System.out.println(daoFactory.getLessonDAO().activateLesson(6));

    System.out.println("attivo 2 lezioni");
    int c = daoFactory.getCourseDAO().selectCourse(1).getId_course();
    int t = daoFactory.getTeacherDAO().selectTeacher(2).getId_teacher();
    System.out.println(daoFactory.getLessonDAO().activateLesson(c,t));

    System.out.println("Tutte le lezioni attive");
    System.out.println(daoFactory.getLessonDAO().selectAllActiveLessons());


    //System.out.println(daoFactory.getLessonDAO().insertLesson(new Lesson(21,4)));
    Teacher te = daoFactory.getTeacherDAO().selectTeacher(3);
    Course co = daoFactory.getCourseDAO().selectCourse(14);
    System.out.println(daoFactory.getLessonDAO().insertLesson(co,te));

    System.out.println(daoFactory.getLessonDAO().selectAllActiveLessons());


    //System.out.println(daoFactory.getLessonDAO().activateLesson(24));
    System.out.println(daoFactory.getLessonDAO().deleteLesson(co.getId_course(),te.getId_teacher()));


    System.out.println(daoFactory.getLessonDAO().selectAllLessons().size());
    System.out.println(daoFactory.getLessonDAO().selectAllActiveLessons().size());

 */
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

  }

}