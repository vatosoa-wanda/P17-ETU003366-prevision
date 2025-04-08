<%@page import="model.Credit" %>
<%@page import="model.Depense" %>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    List<Credit> credits = (List<Credit>) request.getAttribute("credits");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des depenses</title>
    <!-- Bootstrap CSS -->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <!-- Fichier CSS personnalisé -->
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body class="container mt-4">

    <h2 class="text-center text-primary">Liste des dépenses</h2>

    <table class="table table-bordered table-striped mt-4">
        <thead class="table-dark">
            <tr>
                <th>Libelle</th>
                <th>Montant</th>
                <th>Reste</th>
            </tr>
        </thead>
        <tbody>
            <% for (Credit credit : credits) { %>
                <tr>
                    <td><%= credit.getLibelle() %></td>
                    <td><%= credit.getTotalDepenseById() %></td>
                    <td><%= credit.getResteById() %></td>
                </tr>
            <% } %>
        </tbody>
    </table>

</body>
</html>
