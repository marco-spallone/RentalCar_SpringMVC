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

<script type="text/javascript">
  function changeType(selected)
  {
    if(selected === "auto.targa")
      document.getElementById('value').setAttribute("type", "text");
    else
      document.getElementById('value').setAttribute("type", "date");
  }
</script>

<c:choose>
  <c:when test="${isAdmin=='true'}">
    <c:set var="url1" value="customers" />
  </c:when>
  <c:otherwise>
    <c:set var="url1" value="viewReservations?id=${myid}" />
  </c:otherwise>
</c:choose>

<jsp:include page="navbar.jsp">
  <jsp:param name="url1" value="${url1}" />
  <jsp:param name="url2" value="cars"/>
  <jsp:param name="url3" value="userProfile"/>
  <jsp:param name="url4" value="login/form"/>
</jsp:include>
<div class="container">
  <div class="row">
    <div class="mx-auto mt-5 col-sm-8">
      <h3>Prenotazioni effettuate</h3>
      <div class="row"><div class="mt-4 mb-4 col-sm-6">
        <c:choose>
          <c:when test="${isAdmin=='true'}" />
          <c:otherwise>
            <a href="<spring:url value="/addReservation" /> "><button class="btn btn-outline-info">
              <i class="fa-regular fa-calendar-plus fa-lg"></i> Aggiungi prenotazione</button></a>
          </c:otherwise>
        </c:choose>
      </div></div>
      <div class="form-horizontal"><div class="form-group mt-2 mb-2">
        <form method="post" action="filterReservations">
          <select name="field" class="form-select" onchange="changeType(this.value)">
            <option value="dataInizio" selected>Data inizio</option>
            <option value="dataFine">Data fine</option>
            <option value="auto.targa">Targa auto</option>
          </select>
          <input name="value" id="value" type="date" class="form-control mt-2"/>
          <button type="submit" class="mt-3 btn btn-outline-primary"><i class="fa-solid fa-filter"></i></button>
          <a href="viewReservations?id=${id}"><button type="button" class="mt-3 btn btn-outline-danger"><i class="fa-solid fa-x"></i></button></a>
        </form>
      </div></div>
      <div id="tabPrenotazioni">
        <table class="table table-striped table-bordered" id="tab">
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
            <tr>
              <td>${reservation.dataInizio}</td>
              <td>${reservation.dataFine}</td>
              <td>${reservation.auto.targa}</td>
              <td>
                <c:choose>
                  <c:when test="${reservation.confermata==true}"><i class="fa-solid fa-check"></i></c:when>
                  <c:otherwise>
                    <c:choose>
                      <c:when test="${isAdmin=='true'}">
                        <a href="approveReservation?id=${reservation.idPrenotazione}"><button class="btn btn-outline-success">
                          <i class="fa-solid fa-check"></i> Accetta</button></a>
                        <a href="declineReservation?id=${reservation.idPrenotazione}"><button class="btn btn-outline-danger">
                          <i class="fa-solid fa-x"></i> Rifiuta</button></a>
                      </c:when>
                      <c:otherwise />
                    </c:choose>
                  </c:otherwise>
                </c:choose>
              </td>
              <c:choose>
                <c:when test="${isAdmin=='false'}">
                  <td><a href="editReservation?id=${reservation.idPrenotazione}"><button class="mx-auto btn btn-outline-warning"><i class="fa-sharp fa-solid fa-pen fa-lg"></i> Modifica</button></a></td>
                  <td><a href="deleteReservation?id=${reservation.idPrenotazione}"><button class="btn btn-outline-danger"><i class="fa-solid fa-trash fa-lg"></i> Elimina</button></a></td>
                </c:when>
                <c:otherwise></c:otherwise>
              </c:choose>
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
