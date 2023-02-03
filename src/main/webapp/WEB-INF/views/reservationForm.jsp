<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
  <script src="https://kit.fontawesome.com/6b1574191b.js" crossorigin="anonymous"></script>
</head>
<body>
<jsp:include page="navbar.jsp">
    <jsp:param name="url1" value="viewReservations?isAdmin=false&myid=${myid}&id=${myid}" />
    <jsp:param name="url2" value="cars?isAdmin=false&myid=${myid}&id=${myid}"/>
    <jsp:param name="url3" value=""/>
</jsp:include>
<div class="container">
    <div class="row">
        <div class="mx-auto mt-5 col-sm-6">
            <h2>Modifica dati prenotazione</h2>
            <form:form method="post" modelAttribute="newReservation">
                <form:input type="hidden" class="form-control" id="idUtente" path="idUtente" value="${myid}"/>
                <c:choose>
                    <c:when test="${id!=null}">
                        <form:input type="hidden" class="form-control" id="idUtente" path="id" value="${id}"/>
                    </c:when>
                    <c:otherwise />
                </c:choose>
                <div class="mb-3">
                    <label for="inizio" class="form-label">Data inizio: </label>
                    <form:input type="date" class="form-control" id="inizio" path="dataInizio" value="${actualReservation.dataInizio}"/>
                </div>
                <div class="mb-3">
                    <label for="fine" class="form-label">Data fine: </label>
                    <form:input type="date" class="form-control" id="fine" path="dataFine" value="${actualReservation.dataFine}"/>
                </div>
                <button type="submit" class="btn btn-success"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-check" viewBox="0 0 16 16">
                    <path d="M10.97 4.97a.75.75 0 0 1 1.07 1.05l-3.99 4.99a.75.75 0 0 1-1.08.02L4.324 8.384a.75.75 0 1 1 1.06-1.06l2.094 2.093 3.473-4.425a.267.267 0 0 1 .02-.022z"/>
                </svg>Prenota</button>
                <a href="<spring:url value="/viewReservations?isAdmin=false&myid=${myid}&id=${myid}"/> "><button type="button" class="btn btn-warning">Annulla</button></a>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>