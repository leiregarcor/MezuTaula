<%--
Created by IntelliJ IDEA.
  User: leire
  Date: 19/03/2022
  Time: 19:57
  To change this template use File | Settings | File Templates.


  Mezuen zerrenda aurkezteko orria -> TestServlet-etik
  type.equals("getAllMessages") eta format.equals("html") denean
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.*,helper.info.*" %>
<% ArrayList<MessageInfo> messageList = (ArrayList) request.getAttribute("messageList"); %>  <%-- Eskaeratik atributu bat atera --%>

<html>

    <head>
        <title>View Messages</title>
        <link href="/MezuTaula/css/styleSheet.css" rel="stylesheet" />
    </head>

    <body>
        <header>
            <h1>A webapp to share short messages</h1>
            <h3>View Messages</h3>
        </header>

        <section>
            <table>
                <tr>
                    <th>Username</th>
                    <th>Message</th>
                </tr>
                <% for(int i = 0; i < messageList.size(); i++) {
                    MessageInfo messageInfo = messageList.get(i); %>
                <tr>
                    <td><%= messageInfo.getUsername() %></td>
                    <td><%= messageInfo.getMessage() %></td>
                </tr>
                <% } %>
            </table>
        </section>

        <footer>
            Server Date: <%=new Date().toString()%>
            <script type="javascript">
                var fecha = new Date();
                document.write(" -- Client Date: ");
                document.write(fecha);
            </script>
        </footer>

    </body>

</html>
