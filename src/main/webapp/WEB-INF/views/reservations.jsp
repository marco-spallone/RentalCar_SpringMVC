<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
  <script src="https://kit.fontawesome.com/6b1574191b.js" crossorigin="anonymous"></script>
</head>
<body>
<c:choose>
  <c:when test="${isAdmin=='true'}">
    <c:set var="url1" value="customers?isAdmin=true&myid=${myid}" />
  </c:when>
  <c:otherwise>
    <c:set var="url1" value="viewReservations?isAdmin=false&myid=${myid}&id=${myid}" />
  </c:otherwise>
</c:choose>

<jsp:include page="navbar.jsp">
  <jsp:param name="url1" value="${url1}" />
  <jsp:param name="url2" value=""/>
  <jsp:param name="url3" value=""/>
</jsp:include>
<div class="container">
  <div class="row">
    <div class="mx-auto mt-5 col-sm-8">
      <h3>Prenotazioni effettuate</h3>
      <div class="row"><div class="mt-4 mb-4 col-sm-1">
        <c:choose>
          <c:when test="${isAdmin=='true'}"></c:when>
          <c:otherwise>
            <a href="<spring:url value="/addReservation?myid=${myid}" /> "><button class="btn">
              <i class="fa-regular fa-calendar-plus fa-lg" style="color: dodgerblue"></i></button></a>
          </c:otherwise>
        </c:choose>
      </div></div>
      <div id="tabPrenotazioni">
        <table class="table table-striped" id="tab">
          <thead>
          <tr>
            <th scope="col">Data inizio</th>
            <th scope="col">Data fine</th>
            <th scope="col">Auto</th>
            <th scope="col">Confermata</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="reservation" items="${reservations}">
            <fmt:parseNumber var="giorni" value="${( reservation.dataInizio.time - now.time ) / (1000*60*60*24) }"
                             integerOnly="true" scope="page"/>
            <tr>
              <td>${reservation.dataInizio}</td>
              <td>${reservation.dataFine}</td>
              <td>${reservation.auto.marca} ${reservation.auto.modello}</td>
              <td></td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>
</body>
</html>
