package com.tweb.dpd.learn4glory.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ServletAdmin", value = "/ServletAdmin")
public class ServletAdmin extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    response.setContentType("application/json");
    PrintWriter out = response.getWriter();

    HttpSession session = request.getSession();

    switch (request.getParameter("action")) {
      case "loadAllTeachers": {
        if (session.getId().equals(request.getParameter("userSession")) && session.getAttribute("userRole").equals("admin")) {
          System.out.println("faccio metodo loadALL TEACHER");
          loadAllTeachers(request, response, session, out);
        }
      }
      break;

      case "insertTeacher": {
        if (session.getId().equals(request.getParameter("userSession")) && session.getAttribute("userRole").equals("admin")) {
          System.out.println("faccio metodo insertTeacher");
          insertTeacher(request, response, session, out);
        }
      }
      break;

      case "loadTeachersComplete": {
        if (session.getId().equals(request.getParameter("userSession")) && session.getAttribute("userRole").equals("admin")) {
          System.out.println("faccio metodo loadTeachersComplete");
          loadTeachersComplete(request, response, session, out);
        }
      }
      break;

      case "deleteTeacher": {
        if (session.getId().equals(request.getParameter("userSession")) && session.getAttribute("userRole").equals("admin")) {
          System.out.println("faccio metodo deleteTeacher");
          deleteTeacher(request, response, session, out);
        }
      }
      break;

      case "insertCourse": {
        if (session.getId().equals(request.getParameter("userSession")) && session.getAttribute("userRole").equals("admin")) {
          System.out.println("faccio metodo insertCourse");
          insertCourse(request, response, session, out);
        }
      }
      break;

      case "deleteCourse": {
        if (session.getId().equals(request.getParameter("userSession")) && session.getAttribute("userRole").equals("admin")) {
          System.out.println("faccio metodo deleteCourse");
          deleteCourse(request, response, session, out);
        }
      }
      break;

      case "loadCoursesComplete": {
        if (session.getId().equals(request.getParameter("userSession")) && session.getAttribute("userRole").equals("admin")) {
          System.out.println("faccio metodo loadCoursesComplete");
          loadCoursesComplete(request, response, session, out);
        }
      }
      break;

      case "insertTeacherCourseAssociation": {
        if (session.getId().equals(request.getParameter("userSession")) && session.getAttribute("userRole").equals("admin")) {
          System.out.println("faccio metodo insertTeacherCourseAssociation");
          insertTeacherCourseAssociation(request, response, session, out);
        }
      }
      break;


    }

  }

  private void insertTeacherCourseAssociation(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws ServletException, IOException {

    try {
      RequestDispatcher requestDispatcher = getServletContext().getNamedDispatcher("ServletDao");
      requestDispatcher.include(request, response);
      String ris = request.getAttribute("insertTeacherCourseAssociation").toString();
      out.print(ris);
      out.flush();
    } finally {
      out.close();
    }
  }

  private void loadCoursesComplete(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws ServletException, IOException {
    try {
      RequestDispatcher requestDispatcher = getServletContext().getNamedDispatcher("ServletDao");
      requestDispatcher.include(request, response);
      String res = request.getAttribute("loadCoursesComplete").toString();
      request.removeAttribute("loadCoursesComplete");
      out.print(res);
      out.flush();
    } finally {
      out.close();
    }


  }

  private void deleteCourse(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws ServletException, IOException {
    try {
      RequestDispatcher requestDispatcher = getServletContext().getNamedDispatcher("ServletDao");
      requestDispatcher.include(request, response);
      String res = request.getAttribute("deleteCourse").toString();
      request.removeAttribute("deleteCourse");
      out.print(res);
      out.flush();
    } finally {
      out.close();
    }
  }

  private void insertCourse(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws ServletException, IOException {
    try {
      RequestDispatcher requestDispatcher = getServletContext().getNamedDispatcher("ServletDao");
      requestDispatcher.include(request, response);
      String result = request.getAttribute("insertCourse").toString();
      request.removeAttribute("insertCourse");
      out.print(result);
      out.flush();
    } finally {
      out.close();
    }
  }

  private void deleteTeacher(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws ServletException, IOException {
    try {
      RequestDispatcher requestDispatcher = getServletContext().getNamedDispatcher("ServletDao");
      requestDispatcher.include(request, response);
      String res = request.getAttribute("deleteTeacher").toString();
      request.removeAttribute("deleteTeacher");
      out.print(res);
      out.flush();
    } finally {
      out.close();
    }
  }

  private void loadTeachersComplete(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws ServletException, IOException {

    try {
      RequestDispatcher requestDispatcher = getServletContext().getNamedDispatcher("ServletDao");
      requestDispatcher.include(request, response);
      String res = request.getAttribute("loadTeachersComplete").toString();
      request.removeAttribute("loadTeachersComplete");
      out.print(res);
      out.flush();
      out.close();
    } finally {
      out.close();
    }

  }

  private void insertTeacher(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws ServletException, IOException {
    try {
      RequestDispatcher requestDispatcher = getServletContext().getNamedDispatcher("ServletDao");
      requestDispatcher.include(request, response);
      String result = request.getAttribute("insertTeacher").toString();
      request.removeAttribute("insertTeacher");
      out.print(result);
      out.flush();
    } finally {
      out.close();
    }

  }

  private void loadAllTeachers(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws ServletException, IOException {
    try {
      RequestDispatcher requestDispatcher = getServletContext().getNamedDispatcher("ServletDao");
      requestDispatcher.include(request, response);
      String result = request.getAttribute("loadAllTeachers").toString();
      request.removeAttribute("loadAllTeachers");
      out.print(result);
      out.flush();
    } finally {
      out.close();
    }
  }
}
