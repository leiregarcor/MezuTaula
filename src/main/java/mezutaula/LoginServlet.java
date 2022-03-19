package mezutaula;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // request --> HTTP eskaerari erreferentzia egiteko objetua
        // response --> HTTP erantzunari erreferentzia egiteko objetua

        System.out.println("---> LoginServlet servlet-ean sartzen...");

        String email = request.getParameter("email");
        String pasahitza = request.getParameter("password");
        System.out.print("\tEmail: " + email);
        System.out.println("\tPasahitza: " + pasahitza);

        if(email != null && pasahitza !=null) {


            

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
