<%-- 
    Document   : ListaFormula
    Created on : May 5, 2014, 2:01:46 PM
    Author     : desktop02
--%>

<%@page import="java.util.List"%>
<%@page import="br.mack.fci.cc.smh.ejb.Formula"%>
<%@page import="br.mack.fci.cc.smh.ejb.FormulaEJB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    FormulaEJB formula = new FormulaEJB();
    List<Formula> list = formula.listarTodas();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de Formulas</title>
    </head>
    <body>
        <table>
            <tr>
                <th>Nome</th><th>Formula</th><th>Editar</th><th>Excluir</th>
            </tr>
            <%
                for (int i = 0; i < list.size(); i++) {
                    Formula item = list.get(i);
                    out.print("<tr>");
                    out.print("<td>");
                    out.print(item.getNomeFormula());
                    out.print("</td>");
                    out.print("<td>");
                    out.print(item.getFormulaOriginal());
                    out.print("</td>");
            %>
            <td>
                botão;
            </td>
            <td>
                botão;
            </td>
            <%
                    out.print("</tr>");
                }
            %>
        </table>
    </body>
</html>
