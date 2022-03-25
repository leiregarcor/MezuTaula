package mezutaula;

import helper.db.MySQLdb;
import helper.info.MessageInfo;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class MainServlet extends HttpServlet {

    private MySQLdb mySQLdb;

    public MainServlet() {
        super();
        System.out.println("---> Entering init() MainServlet");
        mySQLdb = new MySQLdb();
        System.out.println("---> Exiting init() MainServlet");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("---> MainServlet servlet-ean sartzen...");


        response.setHeader("Cache-Control", "no-cache");
        //PrintWriter http_out = response.getWriter();
       //http_out.println("MainServlet-era heldu zara!");

        HttpSession session = request.getSession(false); // saiao sartuta ez badago, saioa EZ sartu
        if(session == null) {
            System.out.println("\tUser is not logged in");

            System.out.println("\tRedirecting the user to loginForm.html");
            RequestDispatcher rd = request.getRequestDispatcher("/html/loginForm.html");
            rd.forward(request, response);
        } else {
            System.out.println("\tUser is logged in");

            ArrayList<MessageInfo> messageList = mySQLdb.getAllMessages();
            request.setAttribute("messageList", messageList);

            // iraungitze denbora
            int inactive_interval = session.getMaxInactiveInterval();
            request.setAttribute("inactive_interval", inactive_interval);

            System.out.println("\tRedirecting the user to viewMessages.jsp");
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/viewMessages.jsp");
            rd.forward(request, response);


        }
        System.out.println("---> MainServlet servlet-etik irtetzen...");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
