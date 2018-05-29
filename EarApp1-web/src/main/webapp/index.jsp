<!DOCTYPE html>
<%@page import="java.util.*" %>
<html>
    <head>
        <title>Start Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
    Properties sysProp =System.getProperties();
    Set keysSysProp=sysProp.keySet();
%>        
    </head>
    <body>
        <h1>EarApp1-web</h1>
        <h2>
            <a href="webresources/generic">webresources/generic</a>            
        </h2>
        <h2>SYSTEM PROPERTIES</h2>
        <ul>
<%
    for(Object ko:keysSysProp){
        String key = ko.toString();
        String val = sysProp.getProperty(key);
%>                    
            <li><%=key%>=<%=val%></li>
<%
    }
%>                
        <ul>
    </body>
</html>
