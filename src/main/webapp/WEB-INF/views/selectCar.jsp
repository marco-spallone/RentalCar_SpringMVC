<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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

<div class="container">
  <div class="row">
    <div class="mx-auto mt-5 col-sm-6">
      <h2>Seleziona auto tra quelle disponibili per le date indicate:</h2>
      <form:form method="post" modelAttribute="newReservation" action="insert">
        <c:choose>
          <c:when test="${newReservation.id==null}" />
          <c:otherwise>
            <form:input type="hidden" class="form-control" id="inizio" path="id" value="${newReservation.id}"/>
          </c:otherwise>
        </c:choose>
        <form:input type="hidden" class="form-control" id="inizio" path="idUtente" value="${newReservation.idUtente}"/>
        <form:input type="hidden" class="form-control" id="inizio" path="dataInizio" value="${newReservation.dataInizio}"/>
        <form:input type="hidden" class="form-control" id="inizio" path="dataFine" value="${newReservation.dataFine}"/>
        <form:input type="hidden" class="form-control" id="inizio" path="approvata" value="false"/>
        <div class="mb-3">
          <form:select path="idAuto" class="form-select">
            <c:forEach var="car" items="${auto}">
              <form:option value="${car.idAuto}">${car.marca} ${car.modello}</form:option>
            </c:forEach>
          </form:select>
        </div>
        <button type="submit" class="btn btn-success"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-check" viewBox="0 0 16 16">
          <path d="M10.97 4.97a.75.75 0 0 1 1.07 1.05l-3.99 4.99a.75.75 0 0 1-1.08.02L4.324 8.384a.75.75 0 1 1 1.06-1.06l2.094 2.093 3.473-4.425a.267.267 0 0 1 .02-.022z"/>
        </svg>Prenota</button>
        <a href="<spring:url value="/reservations?id=${myid}"/> "><button type="button" class="btn btn-warning">Annulla</button></a>
      </form:form>
    </div>
  </div>
</div>
</body>
</html>
