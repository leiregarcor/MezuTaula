package mezutaula;

import helper.db.MySQLdb;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;

public class LoginServlet extends HttpServlet {
    // Objektu pribatu bat sortuko dugu eta servlet-a 1. aldiz deitzen denean
    // MySQLdb objektu bat instantziatuko da eta objektu honi lotuta geratuko da
    private MySQLdb mySQLdb;

    public LoginServlet() {
        super();
        System.out.println("---> Entering init() LoginServlet");

        mySQLdb = new MySQLdb();

        System.out.println("---> Exiting init() LoginServlet");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // request --> HTTP eskaerari erreferentzia egiteko objetua
        // response --> HTTP erantzunari erreferentzia egiteko objetua

        System.out.println("---> LoginServlet servlet-ean sartzen...");

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        System.out.print("\tEmail: " + email);
        System.out.println("\tPasahitza: " + password);

        if(email != null && password != null) {
            String username = mySQLdb.getUsername(email, password);
            System.out.println("\tRetrieved data from db: " + username);

            // autentikazioa txarto
            if(username == null) {
                System.out.println("\tLogin error: redirecting the user to login form");
                boolean loginerror = true;
                request.setAttribute("login_error", loginerror);
                RequestDispatcher rd = request.getRequestDispatcher("/jsp/loginForm.jsp");
                rd.forward(request, response);

            // autentikazioa ondo
            } else {
                HttpSession session = request.getSession(true); // saioa sortuta ez badago, saioa sortu
                session.setAttribute("username",  username); // saioari username atributu bezela gehitu

                String sessionID = session.getId();
                System.out.println("\tUser session for " + username + ": " + sessionID);

                System.out.print("\tGetting loggedin userlist from servlet context: ");
                ServletContext context = request.getServletContext(); // testuingurua (saio guztiek atzipena dute)
                HashMap<String, String> loggedinUsers = (HashMap) context.getAttribute("loggedin_users"); // hash map, erabiltzaile eta pasahitza gordetzen ditu

                // logeatutako erabiltzaileen zerrenda kudeatu
                if(loggedinUsers == null) { // zerbitzaria abiarazi berri bada (ez erabiltzailerik logeatu)
                    System.out.println("list is empty");
                    loggedinUsers = new HashMap();
                    loggedinUsers.put(username, sessionID);
                } else { // zerbitzarian erabiltzaileak daude jada
                    if(!loggedinUsers.containsKey(username)) {
                        System.out.println(username + " is not in the list");
                        loggedinUsers.put(username, sessionID);
                    } else {
                        System.out.println(username + " is already in the list");
                    }
                }
                System.out.println("\tRedirecting the user to MainServlet");
                RequestDispatcher rd = context.getNamedDispatcher("MainServlet");
                rd.forward(request, response);

            }


        } else if(request.getSession(false) != null) {
            System.out.println("---> User already logged: redirecting to MainServlet");
            ServletContext context = request.getServletContext();
            RequestDispatcher rd = context.getNamedDispatcher("MainServlet");
            rd.forward(request, response);
        } else {
            System.out.println("---> User not logged: redirecting to login form");
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/loginForm.jsp");
            rd.forward(request, response);
        }
        System.out.println("---> LoginServlet servlet-etik irtetzen...");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
