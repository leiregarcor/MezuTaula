package test;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import helper.db.MySQLdb;
import helper.info.MessageInfo;
import com.google.gson.Gson;

public class TestServlet extends HttpServlet {
    // Objektu pribatu bat sortuko dugu eta servlet-a 1. aldiz deitzen denean
    // MySQLdb objektu bat instantziatuko da eta objektu honi lotuta geratuko da
    private MySQLdb mySQLdb;

    public TestServlet() { // Servlet-a eraikitzailea eduki dezakete
        super();
        mySQLdb = new MySQLdb();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("---> Entering doGet() TestServlet");

        PrintWriter http_out = response.getWriter(); // Erantzunaren edukian idazteko
        // erantzunak testu lauean emango ditugunez ez dugu html orri bat sortuko, soilik inprimatuko ditugu terminalean

        // Eskaeratik type parametroa irakurri -> ESPERO DEN PARAMETROA DA
        String type = request.getParameter("type");

        // http://localhost:8080/MezuTaula/servlet/TestServlet?type=...
        if(type != null) {

            // http://localhost:8080/MezuTaula/servlet/TestServlet?type=registerUser&email=leire@gmail.com&password=123&username=lei
            if(type.equals("registerUser")) {
                System.out.println("\tregisterUser has been called");
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String username = request.getParameter("username");
                if(email != null && password !=null && username!=null) { // parametroak ondo sartu direla begiratu
                    System.out.println("\tExtracting request parameters: " + email + " " + password + " " + username);
                    mySQLdb.setUserInfo(email, password, username); // datubasean sartu datuak
                    System.out.println("\tUpdating users table in the database");
                    http_out.println("Ekintza ondo burutu da!");
                } else {
                    http_out.println("registerUser egiteko parametroak ez dira ondo bidali!");
                }

            // http://localhost:8080/MezuTaula/servlet/TestServlet?type=getUsername&email=oskar.casquero@ehu.eus&password=123 ondo dago
            // http://localhost:8080/MezuTaula/servlet/TestServlet?type=getUsername&email=leire@gmail.com&password=123
            } else if(type.equals("getUsername")) {
                System.out.println("\tgetUsername has been called");
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                if(email != null && password !=null) {
                    System.out.println("\tExtracting request parameters: " + email + " " + password);
                    String username = mySQLdb.getUsername(email, password);
                    System.out.println("\tRetrieved data from db: " + username);
                    http_out.println("Aplikazioan kautotu zara: " + username);

                // http://localhost:8080/MezuTaula/servlet/TestServlet?type=getUsername    ez dira parametrorik sartu edo txarto sartu dira
                } else {
                    http_out.println("getUsername egiteko parametroak ez dira ondo bidali!");
                }

            // http://localhost:8080/MezuTaula/servlet/TestServlet?type=registerMessage&username=kaske&message=holitas
            // http://localhost:8080/MezuTaula/servlet/TestServlet?type=registerMessage&username=lei&message=helou
            } else if(type.equals("registerMessage")) {
                System.out.println("\tregisterMessage has been called");
                String username = request.getParameter("username");
                String message = request.getParameter("message");
                if(username != null && message !=null) {
                    System.out.println("\tExtracting request parameters: " + username + " " + message);
                    mySQLdb.setMessageInfo(message, username);
                    System.out.println("\tUpdating messages table in the database");
                    http_out.println("Ekintza ondo burutu da!");

                // http://localhost:8080/MezuTaula/servlet/TestServlet?type=registerMessage
                } else {
                    http_out.println("registerMessage egiteko parametroak ez dira ondo bidali!");
                }

            // http://localhost:8080/MezuTaula/servlet/TestServlet?type=getAllMessages
            } else if(type.equals("getAllMessages")) {

                System.out.println("\tgetAllMessages has been called");
                ArrayList<MessageInfo> messageList = mySQLdb.getAllMessages();
                String format = request.getParameter("format");
                if(format != null) {

                    // http://localhost:8080/MezuTaula/servlet/TestServlet?type=getAllMessages&format=json
                    if(format.equals("json")) {
                        System.out.println("\tConverting ArrayList<MessageInfo> to json");
                        Gson gson = new Gson();
                        String messageList_json = gson.toJson(messageList);
                        System.out.println("\tmessageList_json: " + messageList_json);
                        response.setContentType("application/json");
                        http_out.println(messageList_json);

                    // http://localhost:8080/MezuTaula/servlet/TestServlet?type=getAllMessages&format=html
                    }else if(format.equals("html")) {
                        System.out.println("\tRedirecting the user to viewMessages.jsp");
                        // datuak servlet-etik JSP-ra pasatzeko atributu bat atzituko diogu
                        request.setAttribute("messageList", messageList);
                        RequestDispatcher rd = request.getRequestDispatcher("/jsp/viewMessages.jsp");
                        rd.forward(request, response);

                    }else{
                        http_out.println("format parametroak ez du balio egokia!");
                    }
                }else{
                    http_out.println("getAllMessages egiteko parametroak ez dira ondo bidali!");
                }

            // http://localhost:8080/MezuTaula/servlet/TestServlet?type=adghvjbj  baliozkoa ez den balio bat sartu zaio
            }else {
                http_out.println("type parametroaren balioa ez da zuzena!");
            }

        // http://localhost:8080/MezuTaula/servlet/TestServlet
        }else {
            http_out.println("Ez da 'type' parametrorik bidali!");
        }




        System.out.println("<--- Exiting doGet() TestServlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // request --> HTTP eskaerari erreferentzia egiteko objektua
        // response --> HTTP erantzunari erreferentzia egiteko objektua
        doGet(request, response);
    }
}
