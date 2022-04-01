package mezutaula;

import com.google.gson.Gson;
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

            String format = request.getParameter("format");
            if(format != null) {
                if(format.equals("json")) {
                    PrintWriter http_out = response.getWriter(); // Erantzunaren edukian idazteko

                    System.out.println("\tConverting ArrayList<MessageInfo> to json");
                    ArrayList<MessageInfo> messageList = mySQLdb.getAllMessages();
                    Gson gson = new Gson();
                    String messageList_json = gson.toJson(messageList);
                    System.out.println("\tmessageList_json: " + messageList_json);
                    response.setContentType("application/json");
                    http_out.println(messageList_json);
                }
            } else{
                // ikusi ia mezurik bidali den (datu basean gorde behar diren ala ez mezu berriren bat)
                String message = request.getParameter("message");
                if (message != null) { // erabiltzaileak mezu bat bidali nahi du
                    String username = (String) session.getAttribute("username");
                    mySQLdb.setMessageInfo(message, username); // mezua DB-an gorde
                }

                // mezuen zerrenda atera DB eta eskaeran atributu bezala erantsi gero JSP-ak atributu hori atera dezan
                ArrayList<MessageInfo> messageList = mySQLdb.getAllMessages();
                request.setAttribute("messageList", messageList);

                // iraungitze denbora atera
                int inactive_interval = session.getMaxInactiveInterval();
                request.setAttribute("inactive_interval", inactive_interval);

                // kontrola pasatu jsp-ra
                System.out.println("\tRedirecting the user to viewMessages.jsp");
                RequestDispatcher rd = request.getRequestDispatcher("/jsp/viewMessages.jsp");
                rd.forward(request, response);
            }

        }
        System.out.println("---> MainServlet servlet-etik irtetzen...");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
