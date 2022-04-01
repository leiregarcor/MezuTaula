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
<% ArrayList<MessageInfo> messageList = (ArrayList) request.getAttribute("messageList");%>   <%-- Eskaeratik atributu bat atera --%>
<% int inactive_interval = (int) request.getAttribute("inactive_interval");
    String username = (String) session.getAttribute("username");
    ServletContext context = request.getServletContext();
    HashMap<String, String> loggedinUsers = (HashMap) context.getAttribute("loggedin_users");
%>
<html>

    <head>
        <title>View Messages</title>
        <link href="/MezuTaula/css/styleSheet.css" rel="stylesheet" />

        <script>
            function GetMezuak() {
                // ondorengo objektua erabiliz,
                // JS kodeak HTTP eskaerak egin ditzake
                var request = new XMLHttpRequest(); //AJAX

                // objektu baten atribitu batean funtzio bat kargatuko dugu
                /*
                 * onreadystatechange 	Stores a function (or the name of a function) to be called automatically each time the readyState property changes

                 * readyState 	Holds the status of the XMLHttpRequest. Changes from 0 to 4:
                0: request not initialized
                1: server connection established
                2: request received
                3: processing request
                4: request finished and response is ready

                * status
                200: "OK"
                404: Page not found

                Eskaeraren prozezamenduarekin egoera aldaketa dagoenenean onreadystatechange lotuta duen funtzioa deituko da
                */
                request.onreadystatechange = function () {
                    if (request.readyState == 4){
                        if (request.status == 200){

                            var json = JSON.parse(request.responseText); // json datuak hartu eta parseatu
                            document.getElementsByClassName("mezuenTaula")[0].innerHTML; //class second html documentuan bilatu eta edukia eguneratu eskaeratik lortutatko datuarekin

                        }
                    }
                }

                // eskaera definitu
                request.open("GET", "/MezuTaula/servlet/MainServlet?format=json", true); // uria erlatiboa da zerbitzariarekiko
                request.send(null); //eskaera bidali

                // 1s-rp funtzioa bere burura deitzen du
                setTimeout("GetMezuak()",5000);
                // 1s-ro zenbakiak aldatu behar dira (milisegundotan) , eta esaten zaio ze funtzio deitu behar den
            }
        </script>

    </head>

    <body onload="GetMezuak()">
        <header>
            <h1>A webapp to share short messages</h1>
            <h3>View Messages</h3>
        </header>

        <section>
            <font color="white">You are logged in as: </font>
            <%= username %>
        </section>

        <section>
            <script>
                var timeleft = <%= inactive_interval %>;
                var downloadTimer = setInterval(function() {
                    if(timeleft == 1){
                        clearInterval(downloadTimer);
                    }
                    document.getElementById("progressBar").value = <%= inactive_interval %> - timeleft;
                    timeleft -= 1;
                }, 1000);
            </script>
            <font color="white">Session timeout: </font>
            <progress value="0" max="<%= inactive_interval %>" id="progressBar"></progress>
        </section>

        <section>
            <font color="white">Active users: </font>
            <% for(Map.Entry<String, String> entry : loggedinUsers.entrySet()) { %>
            <%= entry.getKey() %>
            <% } %>
        </section>

        <section>
            <form method="POST" action="/MezuTaula/servlet/MainServlet">
                <table>
                    <tr>
                        <td>Message:</td>
                        <td><textarea id="message" name="message" cols="25" rows="5"></textarea></td>
                    </tr>
                </table>
                <button>Send</button>
            </form>
        </section>

        <section>
            <table id="mezuenTaula" >
                <tr>
                    <th>Username</th>
                    <th>Message</th>
                </tr>
                <!-- java code hau zerbitzarian exekutatzen da eta bezeroak html-a soilik ikusten du -->
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
