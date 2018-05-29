<%-- 
    Document   : sec1
    Created on : 17/05/2018, 05:46:51 PM
    Author     : Alfredo Estrada
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SEC1</title>
    </head>
    <body>
        <h3>SECURE: SEC1</h3>
        <h1>[ 
            <a href="<%=request.getContextPath()%>/secure/home.jsp">HOME</a> |
            <a href="<%=request.getContextPath()%>/secure/sec1.jsp">SEC1</a> | 
            <a href="<%=request.getContextPath()%>/secure/sec2.jsp">SEC2</a> | 
            <a href="<%=request.getContextPath()%>/secure/sec3.jsp">SEC3</a> | 
            <a href="<%=request.getContextPath()%>/secure/sec4.jsp">SEC4</a> | 
            <a href="<%=request.getContextPath()%>/secure/sec5.jsp">SEC5</a>]</h1>
        <h3>
            <a href="<%=request.getContextPath()%>/logout">EXIT</a>
        </h3>
    </body>
</html>
