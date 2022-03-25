package mezutaula;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.HashMap;
import java.util.Map;

@WebListener
public class MySessionListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    public MySessionListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
        System.out.println("---> Sesioa hauzten");

        // sesiotik erabiltzailearen ID-a lortu
        HttpSession session = se.getSession();
        String sessionID = session.getId();
        String username = session.getAttribute("username").toString();
        System.out.println("\t Lortzen erabiltzailearen sessioa " + sessionID + "erabiltzailea: " + username);

        // erabiltzaile zerrenda lortu eta zerrendatik erabiltzailea atera
        ServletContext context = session.getServletContext(); // testuingurua (saio guztiek atzipena dute)
        HashMap<String, String> loggedinUsers = (HashMap) context.getAttribute("loggedin_users"); // hash map, erabiltzaile eta pasahitza gordetzen ditu
        System.out.println("\t Loggedin users: " + loggedinUsers.toString());
        // ezabatu erabiltzailea
        loggedinUsers.remove(username);
        System.out.println("\t Removing " +username+ " from loggedin users");
        System.out.println("\t Loggedin users " + loggedinUsers.toString());

    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is added to a session. */
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is removed from a session. */
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is replaced in a session. */
    }
}
