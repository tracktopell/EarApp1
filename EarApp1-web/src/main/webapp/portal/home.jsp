<%-- 
    Document   : home
    Created on : 17/05/2018, 06:31:48 AM
    Author     : Alfredo Estrada
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HOME</title>
    </head>
    <body>
        <h3>SECURE: HOME</h3>
        <h1>[ 
            <a href="<%=request.getContextPath()%>/portal/home.jsp">HOME</a> |
            <a href="<%=request.getContextPath()%>/portal/sec1.jsp">SEC1</a> | 
            <a href="<%=request.getContextPath()%>/portal/sec2.jsp">SEC2</a> | 
            <a href="<%=request.getContextPath()%>/portal/sec3.jsp">SEC3</a> | 
            <a href="<%=request.getContextPath()%>/portal/sec4.jsp">SEC4</a> | 
            <a href="<%=request.getContextPath()%>/portal/sec5.jsp">SEC5</a> ]</h1>
        <h3>
            <a href="<%=request.getContextPath()%>/logout">EXIT</a>
        </h3>
    </body>
</html>
