<%-- 
    Document   : login
    Created on : 17/05/2018, 09:06:18 AM
    Author     : Alfredo Estrada
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="gob.bancomext.security.SecurityManager"%>
<%@page import="gob.bancomext.security.SecurityToken"%>
<!DOCTYPE html>
<%            
    SecurityToken securityToken=SecurityManager.getInstance().getSecurityToken();    
    System.out.println("["+session.getId()+"][login.jsp] securityToken="+securityToken);    
    session.setAttribute("tokenId", securityToken);
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>login</title>
    </head>
    <body>
        <h1>LOGIN</h1>
        <form action="<%=request.getContextPath()%>/__login" method="POST">
            <input type="text"     name="tokenId" value="<%=securityToken.getTokenId()%>"/>
            user:<input type="text"     name="<%=securityToken.getUserFieldId()%>"/>
            pass:<input type="password" name="<%=securityToken.getPassFieldId()%>"/>
            <input type="submit" value="ENTRAR"/>
        </form>
    </body>
</html>
