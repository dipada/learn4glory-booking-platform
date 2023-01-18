package com.tweb.dpd.learn4glory.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ServletBooking", value = "/ServletBooking")
public class ServletBooking extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json");
    PrintWriter out = response.getWriter();

    HttpSession session = request.getSession();

    System.out.println("Entrato in servletBooking");

    switch (request.getParameter("action")) {
      case "insertBooking": {
        if (session.getId().equals(request.getParameter("userSession"))) {
          insertBooking(request, response, session, out);
        }
      }
      break;

      case "userBookings": {
        System.out.println("Faccio userBookig");
        if (session.getId().equals(request.getParameter("userSession"))) {
          userBookings(request, response, session, out);
        }
      }break;

      case "confirmBooking":{
        System.out.println("Faccio confirmBooking");
        if (session.getId().equals(request.getParameter("userSession"))) {
          confirmBooking(request, response, session, out);
        }
      }break;

      case "deleteBooking":{
        System.out.println("Faccio deleteBooking");
        if (session.getId().equals(request.getParameter("userSession"))) {
          deleteBooking(request, response, session, out);
        }
      }break;

      case "allBookingsAdmin": {
        if (session.getId().equals(request.getParameter("userSession")) && session.getAttribute("userRole").equals("admin")){
          System.out.println("Faccio allBookingsAdmin");
          allBookingsAdmin(request, response, session, out);
        }
      }break;
    }
  }

  private void allBookingsAdmin(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws ServletException, IOException {
    RequestDispatcher requestDispatcher = getServletContext().getNamedDispatcher("ServletDao");
    requestDispatcher.include(request, response);
    out.print(request.getAttribute("allBookingsAdmin"));
    request.removeAttribute("allBookingsAdmin");
    out.flush();
    out.close();
  }

  private void deleteBooking(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws ServletException, IOException {
    RequestDispatcher requestDispatcher = getServletContext().getNamedDispatcher("ServletDao");
    requestDispatcher.include(request, response);
    out.print(request.getAttribute("deleteBooking"));
    request.removeAttribute("deleteBooking");
    out.flush();
    out.close();
  }

  private void confirmBooking(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws ServletException, IOException {
    try {
      RequestDispatcher requestDispatcher = getServletContext().getNamedDispatcher("ServletDao");
      requestDispatcher.include(request, response);
      String result = request.getAttribute("confirmBooking").toString();
      request.removeAttribute("confirmBooking");
      System.out.println(" in servletBooking result: " + result);
      if (result.equals("true")) {
        out.print(result);
      }
      out.flush();
    }finally {
      out.close();
    }
  }

  private void userBookings(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws ServletException, IOException {
    try {
      System.out.println("Arrivato a userBooking");
      RequestDispatcher requestDispatcher = getServletContext().getNamedDispatcher("ServletDao");
      requestDispatcher.include(request, response);
      String result = request.getAttribute("listBookingUser").toString();
      request.removeAttribute("listBookingUser");
      if (!(result.equals("[]"))) {
        out.print(result);
      }
      out.flush();
    } finally {
      out.close();
    }
  }


  private void insertBooking(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws ServletException, IOException {

      RequestDispatcher requestDispatcher = getServletContext().getNamedDispatcher("ServletDao");
      requestDispatcher.include(request, response);
      String result = request.getAttribute("insertBooking").toString();
      request.removeAttribute("insertBooking");

      out.print(result);
      out.flush();
      out.close();
  }
}
