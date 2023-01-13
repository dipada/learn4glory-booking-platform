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

      case "logout":{
        HttpSession session = request.getSession(false);
        synchronized (getServletContext()){
          session.removeAttribute("userEmail");
          session.removeAttribute("userRole");
        }
        session.invalidate();
        out.close();
      }

      case "imLogged": {
        HttpSession session = request.getSession(false); //if exist take session else return null
        Gson gson = new Gson();
        Map<String, String> toClient = new HashMap<>();
        if (session != null && session.getAttribute("userEmail") != null && session.getAttribute("userRole") != null) {
          toClient.put("userSession", session.getId());

          /*toClient.put("userEmail", session.getAttribute("userEmail").toString());
            toClient.put("userRole", session.getAttribute("userRole").toString());
           */
        } else {
          System.out.println("nell else");
          toClient.put("userSession", "no session");
        }

        out.print(gson.toJson(toClient));
      }
      break;

      case "reqUserInfo": {
        HttpSession session = request.getSession(false);
        Map<String, String> toClient = new HashMap<>();

        if (session != null && session.getAttribute("userEmail") != null && session.getAttribute("userRole") != null) {
          toClient.put("userSession", session.getId());
          toClient.put("userEmail", session.getAttribute("userEmail").toString());
          toClient.put("userRole", session.getAttribute("userRole").toString());
        } else {
          toClient.put("userSession", "no data");
        }
        Gson gson = new Gson();
        out.print((gson.toJson(toClient)));
      }
      break;


    }
  }

  private void SignUp(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws ServletException, IOException {

    String username = request.getParameter("username");
    String userEmail = request.getParameter("email");
    String userPwd = request.getParameter("password");
    // other form fields are ignored

    String result = null;


    if (username != null && userEmail != null && userPwd != null && Pattern.compile("[a-zA-z0-9]+").matcher(username).matches() && Pattern.compile("[a-z0-9.-]+@[a-z0-9.-]+\\.[a-z]{2,4}$").matcher(userEmail).matches() && Pattern.compile("[a-zA-Z0-9]{3,6}").matcher(userPwd).matches()) {
      synchronized (getServletContext()) { // https://coderanch.com/t/637206/java/Understanding-synchronization-ServletContext-attributes
        session.setAttribute("userEmail", userEmail); // for new session if all goes good
      }

      RequestDispatcher requestDispatcher = getServletContext().getNamedDispatcher("ServletDao");
      requestDispatcher.include(request, response);

      result = request.getAttribute("userRole").toString();
      request.removeAttribute("userRole");
    }

    Gson gson = new Gson();

    if (result == null || result.equals("userAlreadyExist")) {
      response.setContentType("text/html;charset=UTF-8");
      System.out.println("esiste");
      synchronized (getServletContext()) {
        session.removeAttribute("userEmail");
      }

      // TODO redirect and alert that user exist on form submit
      response.sendRedirect("http://localhost:8081/learn4glory_war_exploded/");
      //out.print(gson.toJson("userAlreadyExist"));
    } else {

      response.sendRedirect("http://localhost:8081/learn4glory_war_exploded/");
      synchronized (getServletContext()) {
        session.setAttribute("userRole", result);
      }
      String sessionID = session.getId();

      //out.print(gson.toJson(sessionID));
    }
    out.flush();
    out.close();
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
