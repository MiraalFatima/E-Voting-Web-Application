<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.sql.*" %>

<!DOCTYPE html>
<html>
<head>
    <title>Candidates List</title>
</head>
<body>
    <h1>Candidates List</h1>
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Party Name</th>
            <th>Party ID</th>
            <th>Symbol</th>
        </tr>
        <c:forEach var="candidate" items="${candidates}">
            <tr>
                <td>${candidate.name}</td>
                <td>${candidate.partyName}</td>
                <td>${candidate.partyId}</td>
                <td><img src="data:image/jpeg;base64,${candidate.symbol}" alt="Symbol"/></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
