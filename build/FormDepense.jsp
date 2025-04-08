<%@page import="model.Credit" %>
<%@page import="model.Depense" %>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    List<Credit> credits = (List<Credit>) request.getAttribute("credits");
  
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/form.css">
    <title>Depense</title>
</head>
<body>
    <form action="depense" method="post">
        <div>
            <label for="idCredit">Libelle</label>
            <select class="form-control" id="idCredit" name="idCredit">
                <% for (Credit credit : credits) { %>
                    <option value="<%= credit.getId() %>">
                        <%= credit.getLibelle() %>
                    </option>
                <% } %>
            </select>
        </div>
        <div>
            <label for="montant">Montant</label>
            <input type="number" id="montant" name="montant">
        </div>
        <div>
            <label for="date">Date</label>
            <input type="date" id="date" name="date">
        </div>

        <button type="submit">Ajouter</button>
    </form>

    <div>
        <button><a href="<%= request.getContextPath() %>/depense">Liste des depenses</a></button>
    </div>
</body>
</html>
