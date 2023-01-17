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

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();

        switch (request.getParameter("action")){
            case "insertBooking":{
                insertBooking(request, response, session, out);
            }
            break;
        }
  }



  private void insertBooking(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws ServletException, IOException{
      try {
          RequestDispatcher requestDispatcher = getServletContext().getNamedDispatcher("ServletDao");
          requestDispatcher.include(request, response);
          String result = request.getAttribute("insertBooking").toString();
          request.removeAttribute("insertBooking");
          if (!(result.equals("[]"))) {
              out.print(result);
          }else {
              out.print("");
          }
          out.flush();
      } finally {
          out.close();
      }
  }
}
