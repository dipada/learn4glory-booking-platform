package com.tweb.dpd.learn4glory.controller;

import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ServletSession", value = "/ServletSession")
public class ServletSession extends HttpServlet {
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

    HttpSession session = request.getSession(); //if exist take sessionId else make new one
    PrintWriter out = response.getWriter();

    switch (request.getParameter("action")) {
      case "login": {
        Login(request, response, session, out);
      }
      break;

      case "signUp": {
        //TODO
      }
      break;
    }
  }

  private void Login(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws ServletException, IOException {
    String userEmail = request.getParameter("userEmail");
    String result = null;

    if (userEmail != null && request.getParameter("userPwd") != null) {
      session.setAttribute("userEmail", userEmail);

      RequestDispatcher requestDispatcher = getServletContext().getNamedDispatcher("ServletDao");  //forward to ServletDao
      requestDispatcher.include(request, response);

      result = request.getAttribute("userRole").toString();

      request.removeAttribute("userRole");
    }

    Gson gson = new Gson();
    if (result == null || result.equals("notExist")) {
      session.removeAttribute("userEmail");
      out.print(gson.toJson("no session"));
    } else {
      // session exist here
      session.setAttribute("userRole", result);
      String sessionID = session.getId();
      out.print(gson.toJson(sessionID));
    }
    out.flush();
  }
}
