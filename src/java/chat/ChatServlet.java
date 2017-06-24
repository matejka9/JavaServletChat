/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author petrovic16
 */
public class ChatServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            HttpSession session = request.getSession();
            ServletContext app = session.getServletContext();
            String msgList = (String)app.getAttribute("msgs");
            if (msgList == null)  msgList = "";  
            
            ArrayList<String> userList = (ArrayList<String>)app.getAttribute("usrList");
            if (userList == null) userList = new ArrayList<String>();                
            
            String username = (String)session.getAttribute("username");
            if (username == null)
            {
                username = request.getParameter("username");
                session.setAttribute("username", username);
                userList.add(username);
            }
            
            String logoutParam = request.getParameter("logout");
            if (logoutParam != null)
            {
                msgList = "";
                app.setAttribute("msgs", msgList);
            }
            
            else {
            String clearParam = request.getParameter("clear");
            if (clearParam != null)
            {
                msgList = "";
                app.setAttribute("msgs", msgList);
            }
            else
            {
                String refreshParam = request.getParameter("refresh");
                if (refreshParam == null)
                {
                    String sendParam = request.getParameter("send");
                    if (sendParam != null)
                        if (sendParam.equals("Send"))
                            msgList += ((String)session.getAttribute("username")) + 
                                       ": " + request.getParameter("msg") + "<br>";

                    app.setAttribute("msgs", msgList);
                }
            }
            
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ChatServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChatServlet at " + request.getContextPath() + "</h1>");
            out.println("messages:<br><br>" + msgList);
            out.println("<form action=\"chat\" method=\"POST\">Enter new msg:");
            out.println("<input type=\"text\" name=\"msg\">");
            out.println("<input type=\"submit\" name=\"send\" value=\"Send\"><br>");
            out.println("<input type=\"submit\" name=\"refresh\" value=\"Refresh\">");
            out.println("<input type=\"submit\" name=\"clear\" value=\"Clear\">");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}