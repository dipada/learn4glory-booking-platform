package com.tweb.dpd.learn4glory.dao;

import com.google.gson.Gson;
import com.tweb.dpd.learn4glory.model.LessonWrapper;
import com.tweb.dpd.learn4glory.model.Course;
import com.tweb.dpd.learn4glory.model.Lesson;
import com.tweb.dpd.learn4glory.model.Teacher;

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



    /*
    String ss = request.getParameter("action");
    System.out.println(ss);


     */

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

      case "signup":
        break;

      case "teachersOfCourse": {
        int course_id = Integer.parseInt(request.getParameter("course_id"));
        /*System.out.println("corso id " + course_id);
        System.out.println(teacherDAO.selectTeacherByCourseId(course_id));

         */

        String s = gson.toJson(teacherDAO.selectTeachersByCourseId(course_id));
        out.print(s);
        out.flush();
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
        List<Lesson> lessons = lessonDAO.selectAllLessons();

        List<LessonWrapper> lessonWrappers = new ArrayList<>();
        for (Lesson l : lessons) {
          Course tempCourse = courseDAO.selectCourse(l.getCourse());
          Teacher tempTeacher = teacherDAO.selectTeacher(l.getTeacher());
          LessonWrapper lessonWrapper = new LessonWrapper(l.getId_lesson(), tempCourse, tempTeacher, l.getWeek_day(), l.getHour(), l.isActive());
          lessonWrappers.add(lessonWrapper);
        }

        String s = gson.toJson(lessonWrappers);
        out.print(s);
        out.flush();
      }
      break;

    }


  }
}
