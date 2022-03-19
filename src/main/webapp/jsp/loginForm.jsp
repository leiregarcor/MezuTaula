<%--
Created by IntelliJ IDEA.
  User: leire
  Date: 19/03/2022
  Time: 19:57
  To change this template use File | Settings | File Templates.


  LOGIN orria -> autentikatzeko
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

    <head>
        <title>Login Form</title>
        <link href="/MezuTaula/css/styleSheet.css" rel="stylesheet" />
    </head>

    <body>
        <header>
            <h1>A webapp to share short messages</h1>
            <h3>Log in form</h3>
        </header>

        <%  Object login_error_aux = request.getAttribute("login_error");
            if(login_error_aux != null) {
                if((boolean) login_error_aux) { %>
                    <h3>LOGIN ERROR!!!</h3>
                <% }
            } %>

        <section>
            <form method="POST" action="/MezuTaula/servlet/LoginServlet">
                <table>
                    <tr>
                        <td>Email:</td>
                        <td><input name="email"/></td>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td><input type="password" name="password"/></td>
                    </tr>
                </table>
                <button>Send</button>
            </form>
        </section>

        <footer>Web Systems - EUITI Bilbao</footer>
    </body>

</html>
