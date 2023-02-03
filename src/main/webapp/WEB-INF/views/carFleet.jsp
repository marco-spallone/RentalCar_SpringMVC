<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <jsp:param name="url2" value="cars?isAdmin=${isAdmin}&myid=${myid}&id=${myid}"/>
    <jsp:param name="url3" value=""/>
</jsp:include>

<div class="container">
    <div class="row">
        <div class="mx-auto mt-5 col-md-6 col-sm-6">
            <h3 class="page-title">Customers</h3>
            <c:choose>
                <c:when test="${isAdmin=='true'}">
                    <div class="mt-4 mb-4"><a href="<spring:url value="/addCar?myid=${myid}" />">
                        <button class="btn"><i class="fa-solid fa-car fa-lg" style="color: dodgerblue"></i></button></a>
                    </div>
                </c:when>
                <c:otherwise />
            </c:choose>
            <div id="tabAuto">
                <table class="table table-striped table-bordered" id="tab">
                    <thead>
                    <tr>
                        <th scope="col">Marca</th>
                        <th scope="col">Modello</th>
                        <th scope="col">Anno</th>
                        <th scope="col">Prezzo</th>
                        <th scope="col">Targa</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="car" items="${cars}">
                        <tr>
                            <td>${car.marca}</td>
                            <td>${car.modello}</td>
                            <td>${car.anno}</td>
                            <td>${car.prezzo}</td>
                            <td>${car.targa}</td>
                            <c:choose>
                                <c:when test="${isAdmin=='true'}">
                                    <td><a href="<spring:url value='/editCar?id=${car.idAuto}&myid=${myid}' />">Modifica</a></td>
                                    <td><a href="<spring:url value='/deleteCar?id=${car.idAuto}&myid=${myid}' />">Elimina</a></td>
                                </c:when>
                                <c:otherwise />
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
