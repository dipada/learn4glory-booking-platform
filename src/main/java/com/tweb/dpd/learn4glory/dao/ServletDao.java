package com.tweb.dpd.learn4glory.dao;

import com.google.gson.Gson;
import com.tweb.dpd.learn4glory.model.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ServletDao", value = "/ServletDao", loadOnStartup = 1, asyncSupported = true)
public class ServletDao extends HttpServlet {

  private DAOFactory daoFactory;

  private BookedLessonDAO bookedLessonDAO;
  private CourseDAO courseDAO;
  private TeacherDAO teacherDAO;
  private LessonDAO lessonDAO;
  private UserDAO userDAO;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    ServletContext servletContext = config.getServletContext();
    String url = servletContext.getInitParameter("DB-URL");
    String user = servletContext.getInitParameter("user");
    String pwd = servletContext.getInitParameter("pwd");


    // Getting dao factory for MYSQL datasource
    daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL, new String[]{url, user, pwd});

    // Setting all dao references

    if (daoFactory != null) {
      bookedLessonDAO = daoFactory.getBookedLessonDAO();
      courseDAO = daoFactory.getCourseDAO();
      teacherDAO = daoFactory.getTeacherDAO();
      lessonDAO = daoFactory.getLessonDAO();
      userDAO = daoFactory.getUserDAO();
    }

    //servletContext.setAttribute("daoFactory", daoFactory);
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
    // TODO gestire sessione

    System.out.println("ookok");
    response.setContentType("application/json");
    PrintWriter out = response.getWriter();
    Gson gson = new Gson();

    HttpSession session = request.getSession();

    switch (request.getParameter("action")) {
      case "login": {
        String userEmail = (String) session.getAttribute("userEmail");
        String userPwd = request.getParameter("userPwd");
        if (userDAO.emailExist(userEmail) && userDAO.selectUserPassword(userEmail).equals(userPwd)) {
          if (userDAO.selectUser(userEmail).isAdmin()) {
            request.setAttribute("userRole", "admin");
          } else {
            request.setAttribute("userRole", "client");
          }
        } else {
          request.setAttribute("userRole", "notExist");
        }
      }
      break;

      case "signUp": {
        // TODO controllare
        String userEmail = (String) session.getAttribute("userEmail");
        String username = request.getParameter("username");
        String userPwd = request.getParameter("password");

        System.out.println(userEmail + " " + username + " " + userPwd);
        if (!userDAO.emailExist(userEmail) || !userDAO.usernameExist(username)) {
          //make new user
          userDAO.inserUser(username, userEmail, userPwd);

          request.setAttribute("userRole", "client");
        } else {
          request.setAttribute("userRole", "userAlreadyExist");


        }
      }
      break;
        /*
        String userEmail = request.getParameter("userEmail");
        String userPwd = request.getParameter("userPwd");
        System.out.println(userEmail + " " + userPwd);
        if(userDAO.emailExist(userEmail) && userDAO.selectUserPassword(userEmail).equals(userPwd)){
          // Make session if not exist
          System.out.println("okokokokokok");
          out.print(gson.toJson("Login effettuato"));
          out.flush();

        }else {
          // login fail, wrong email and password
          out.print(gson.toJson("no session"));
          out.flush();
        }


      }
      break;*/

      case "dateOfCourse": {
        int course_id = Integer.parseInt(request.getParameter("course_id"));
        //System.out.println(gson.toJson(lessonDAO.selectLessonsCourseId(course_id)));
        String s = gson.toJson(lessonDAO.selectLessonsCourseId(course_id));
        System.out.println(s);
        out.print(s);
        out.flush();
        out.close();

      }
      break;

      case "teachersOfCourse": {
        int course_id = Integer.parseInt(request.getParameter("course_id"));
        /*System.out.println("corso id " + course_id);
        System.out.println(teacherDAO.selectTeacherByCourseId(course_id));

         */

        String s = gson.toJson(teacherDAO.selectTeachersByCourseId(course_id));
        out.print(s);
        out.flush();
        out.close();
      }
      break;

      case "allCourse": {
        String s = gson.toJson(courseDAO.selectAllAvailableCourses());
        out.print(s);
        out.flush();
      }
      break;

      case "lessons": {
        // System.out.println("get arrivata");
        List<Lesson> lessons = lessonDAO.selectAllActiveLessons();

        List<LessonWrapper> lessonWrappers = new ArrayList<>();
        for (Lesson l : lessons) {
          Course tempCourse = courseDAO.selectCourse(l.getCourse());
          Teacher tempTeacher = teacherDAO.selectTeacher(l.getTeacher());
          LessonWrapper lessonWrapper = new LessonWrapper(l.getId_lesson(), tempCourse, tempTeacher, l.getWeek_day(), l.getHour(), l.isActive());
          lessonWrappers.add(lessonWrapper);
        }

        String s = gson.toJson(lessonWrappers);
        out.print(s);
        System.out.println("ARRA ");
        System.out.println(s);
        out.flush();
        out.close();
      }
      break;

      case "lessonsOfTeacherCourse": {
        List<Lesson> lessonsTeacher = lessonDAO.selectAllTeacherLessons(Integer.parseInt(request.getParameter("courseid")), Integer.parseInt(request.getParameter("teacherid")));

        List<LessonWrapper> lessonWrappers = new ArrayList<>();
        for (Lesson l : lessonsTeacher) {
          Course tempCourse = courseDAO.selectCourse(l.getCourse());
          Teacher tempTeacher = teacherDAO.selectTeacher(l.getTeacher());
          LessonWrapper lessonWrapper = new LessonWrapper(l.getId_lesson(), tempCourse, tempTeacher, l.getWeek_day(), l.getHour(), l.isActive());
          lessonWrappers.add(lessonWrapper);
        }

        String s = gson.toJson(lessonWrappers);
        out.print(s);
        System.out.println("Lesson teacher ");
        System.out.println(s);
        out.flush();
        out.close();

      }
      break;

      case "allBookedLesson": {
        List<BookedLesson> bookedLessons = bookedLessonDAO.selectAllBookedLessons();
        String s = gson.toJson(bookedLessons);
        out.print(s);
        System.out.println("BOOOKED ");
        System.out.println(s);
        out.flush();
        out.close();
      }
      break;

      case "insertBooking": {
        User user = userDAO.selectUser((String) session.getAttribute("userEmail"));
        Lesson lesson = lessonDAO.selectLesson(Integer.parseInt(request.getParameter("lessonid")));
        String s = gson.toJson(bookedLessonDAO.insertBookedLesson(user, lesson));
        System.out.println("insert booking " + s);
        request.setAttribute("insertBooking", s);
      }
      break;

      case "userBookings": {
        User user = userDAO.selectUser((String) session.getAttribute("userEmail"));

        List<BookedLesson> bookedLessons = bookedLessonDAO.selectAllBookedLessonsOfUser(user.getId_user());

        List<BookedLessonWrapper> bookedLessonWrappers = new ArrayList<>();

        for (BookedLesson b : bookedLessons) {
          Lesson lesson = lessonDAO.selectLesson(b.getLesson());
          LessonWrapper lessonWrapper = new LessonWrapper(lesson.getId_lesson(), courseDAO.selectCourse(lesson.getCourse()), teacherDAO.selectTeacher(lesson.getTeacher()), lesson.getWeek_day(), lesson.getHour(), lesson.isActive());

          BookedLessonWrapper booking = new BookedLessonWrapper(b.getId_booking(), user, lessonWrapper, b.getWeek_day(), b.getHour(), b.isCompleted(), b.isDeleted());

          bookedLessonWrappers.add(booking);
        }

        String s = gson.toJson(bookedLessonWrappers);
        System.out.println(s);
        request.setAttribute("listBookingUser", s);
      }
      break;

      case "confirmBooking": {
        String s = gson.toJson(bookedLessonDAO.markLessonCompleted(Integer.parseInt(request.getParameter("bookingId"))));
        request.setAttribute("confirmBooking", s);
        System.out.println(" in confirm booking " + s);
      }
      break;

      case "deleteBooking": {
        String s = gson.toJson(bookedLessonDAO.markLessonDeleted(Integer.parseInt(request.getParameter("bookingId"))));
        request.setAttribute("deleteBooking", s);
        System.out.println(" in delete booking " + s);
      }
      break;

      case "allBookingsAdmin": {
        System.out.println("in all bookings admin");
        List<BookedLesson> bookedLessons = bookedLessonDAO.selectAllBookedLessons();

        List<BookedLessonWrapper> resultBookings = new ArrayList<>();

        for (BookedLesson b : bookedLessons) {
          User user = userDAO.selectUser(b.getUser());
          Lesson lesson = lessonDAO.selectLesson(b.getLesson());
          LessonWrapper lessonWrapper = new LessonWrapper(lesson.getId_lesson(), courseDAO.selectCourse(lesson.getCourse()), teacherDAO.selectTeacher(lesson.getTeacher()), lesson.getWeek_day(), lesson.getHour(), lesson.isActive());
          BookedLessonWrapper booking = new BookedLessonWrapper(b.getId_booking(), user, lessonWrapper, b.getWeek_day(), b.getHour(), b.isCompleted(), b.isDeleted());
          resultBookings.add(booking);
        }


        String s = gson.toJson(resultBookings);
        System.out.println(s);
        request.setAttribute("allBookingsAdmin", s);

      }
      break;

      case "loadAllTeachers": {
        String s = gson.toJson(teacherDAO.selectAllActiveTeachers());
        System.out.println("In loadAllteachers " + s);
        request.setAttribute("loadAllTeachers", s);
      }
      break;

      case "insertTeacher": {
        String res;
        if (teacherDAO.insertTeacher(request.getParameter("teacherName"), request.getParameter("teacherSurname")) != -1) {
          res = "true";
        } else {
          res = "false";
        }
        String s = gson.toJson(res);
        System.out.println("In insert TEACHER" + s);
        request.setAttribute("insertTeacher", s);
      }
      break;

      case "loadTeachersComplete": {
        String s = gson.toJson(teacherDAO.selectAllTeachers());
        System.out.println("Dentro loadteachercompelte" + s);
        request.setAttribute("loadTeachersComplete", s);
      }
      break;

      case "deleteTeacher": {
        System.out.println(request.getParameter("teacherId"));
        System.out.println("Dentro delete teacher");
        List<Lesson> listLessonToRemove = lessonDAO.selectAllTeacherLessons(Integer.parseInt(request.getParameter("teacherId")));
        List<BookedLesson> bookedLessons = bookedLessonDAO.selectAllBookedLessons();

        for (Lesson l : listLessonToRemove) {
          for (BookedLesson b : bookedLessons) {
            if (b.getLesson() == l.getId_lesson()) {
              bookedLessonDAO.markLessonDeleted(b.getId_booking());
            }
          }
          lessonDAO.deleteLesson(l.getId_lesson());
        }

        String s = gson.toJson(teacherDAO.deleteTeacher(Integer.parseInt(request.getParameter("teacherId"))));
        System.out.println("Dentro delete teacher" + s);
        request.setAttribute("deleteTeacher", s);
      }
      break;

      case "insertCourse": {
        String res;
        if (courseDAO.insertCourse(request.getParameter("courseName")) != -1) {
          res = "true";
        } else {
          res = "false";
        }
        String s = gson.toJson(res);
        System.out.println("In insert COURSE" + s);
        request.setAttribute("insertCourse", s);
      }
      break;

      case "deleteCourse": {
        List<Lesson> listLessonToRemove = lessonDAO.selectAllLessonsOfCourse(Integer.parseInt(request.getParameter("courseId")));
        List<BookedLesson> bookedLessons = bookedLessonDAO.selectAllBookedLessons();

        for (Lesson l : listLessonToRemove) {
          for (BookedLesson b : bookedLessons) {
            if (b.getLesson() == l.getId_lesson()) {
              bookedLessonDAO.markLessonDeleted(b.getId_booking());
            }
          }
          lessonDAO.deleteLesson(l.getId_lesson());
        }

        String s = gson.toJson(courseDAO.deleteCourse(Integer.parseInt(request.getParameter("courseId"))));
        System.out.println("Dentro delete course" + s);
        request.setAttribute("deleteCourse", s);
      }
      break;

      case "loadCoursesComplete": {
        String s = gson.toJson(courseDAO.selectAllCourses());
        System.out.println("Dentro loadcoursecompelte" + s);
        request.setAttribute("loadCoursesComplete", s);
      }
      break;

      case "insertTeacherCourseAssociation": {
        Course course = courseDAO.selectCourse(Integer.parseInt(request.getParameter("courseId")));
        Teacher teacher = teacherDAO.selectTeacher(Integer.parseInt(request.getParameter("teacherId")));
        WEEK_DAY week_day = WEEK_DAY.valueOf(request.getParameter("day"));
        int hour = Integer.parseInt(request.getParameter("hour"));

        String res;

        if (lessonDAO.insertLesson(course, teacher, week_day, hour) != -1) {
          res = "true";
        } else {
          res = "false";
        }

        String s = gson.toJson(res);
        request.setAttribute("insertTeacherCourseAssociation", s);
        System.out.println("In insert TEACHER COURSE ASSOCIATION" + s);
      }
      break;


    }


  }
}
