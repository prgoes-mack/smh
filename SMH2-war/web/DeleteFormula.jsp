<%-- 
    Document   : DeleteFormula
    Created on : May 5, 2014, 3:09:42 PM
    Author     : desktop02
--%>

<%@page import="br.mack.fci.cc.smh.ejb.FormulaEJB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String value = request.getParameter("nome");
    FormulaEJB form = new FormulaEJB();
    form.removerFormula(value);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Deletar Formula</title>
    </head>
    <body>
        Formula "<%=value%>" deletada com sucesso.
        <a href="ListaFormula.jsp">Voltar</a>
    </body>
</html>
