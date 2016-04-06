package org.legislaturachaco.sgTrabajosInformaticos.utilidades;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author coperalta
 */
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Extend HttpServlet class

@WebServlet("/ManejarError")
public class ManejarError extends HttpServlet {
 
  // Method to handle GET method request.
  @Override
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
      // Analyze the servlet exception       
      Throwable throwable = (Throwable)
      request.getAttribute("javax.servlet.error.exception");
      Integer statusCode = (Integer)
      request.getAttribute("javax.servlet.error.status_code");
      String servletName = (String)
      request.getAttribute("javax.servlet.error.servlet_name");
      if (servletName == null){
         servletName = "Unknown";
      }
      String requestUri = (String)
      request.getAttribute("javax.servlet.error.request_uri");
      if (requestUri == null){
         requestUri = "Unknown";
      }

      // Set response content type
      response.setContentType("text/html");
 
      PrintWriter out = response.getWriter();
	  String title = "Error/Exception Information";
      String docType =
      "<!doctype html public \"-//w3c//dtd html 4.0 " +
      "transitional//en\">\n";
      out.println(docType +
        "<html>\n" +
        "<head><title>" + title + "</title></head>\n" +
        "<body bgcolor=\"#f0f0f0\">\n");

      if (throwable == null && statusCode == null){
         out.println("<h2>Error information is missing</h2>");
         out.println("Please return to the <a href=\"" + 
           response.encodeURL("http://localhost:8080/") + 
           "\">Home Page</a>.");
      }else if (statusCode != null){
         out.println(servletName+" "+requestUri);
         out.println("Código de error: " + statusCode);
      }else{
         out.println("<h2>Error information</h2>");
         out.println("Servlet Name : " + servletName + 
                             "</br></br>");
         out.println("Exception Type : " + 
                             throwable.getClass( ).getName( ) + 
                             "</br></br>");
         out.println("The request URI: " + requestUri + 
                             "<br><br>");
         out.println("The exception message: " + 
                                 throwable.getMessage( ));
      }
      out.println("</body>");
      out.println("</html>");
  }
  // Method to handle POST method request.
  @Override
  public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {
     doGet(request, response);
  }
}