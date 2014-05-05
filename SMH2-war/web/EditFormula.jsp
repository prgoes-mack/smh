<%-- 
    Document   : EditFormula
    Created on : May 5, 2014, 3:09:42 PM
    Author     : desktop02
--%>

<%@page import="br.mack.fci.cc.smh.ejb.Formula"%>
<%@page import="br.mack.fci.cc.smh.ejb.FormulaEJB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String value = request.getParameter("nome");
    FormulaEJB form = new FormulaEJB();
    Formula tempFormula = form.encontrarFormula(value);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar Formula</title>
    </head>
    <body>
        <form method="POST" action="/SMH2-war/AlterarFormula">
            <label for="nome">Nome da Formula:</label>
            <input type="text" name="nome" value="<%=tempFormula.getNomeFormula()%>"/>
            <br/>
            <label for="nome">Formula:</label>
            <input type="text" name="formula" value="<%=tempFormula.getFormulaOriginal()%>"/>
            <br/>
            <input type="submit"/>
        </form>
    </body>
</html>
