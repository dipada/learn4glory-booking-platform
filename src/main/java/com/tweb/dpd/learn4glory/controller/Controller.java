package com.tweb.dpd.learn4glory.controller;

import com.tweb.dpd.learn4glory.dao.DAOFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

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