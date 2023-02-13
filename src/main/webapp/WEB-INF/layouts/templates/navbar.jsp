<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Navbar</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
    <script src="https://kit.fontawesome.com/6b1574191b.js" crossorigin="anonymous"></script>
</head>
<body>
<sec:authentication property="principal.isAdmin" var="isAdmin" />
<sec:authentication property="principal.id" var="myid" />
<c:choose>
    <c:when test="${isAdmin}">
        <c:url var="url1" value="/customers" />
    </c:when>
    <c:otherwise>
        <c:url var="url1" value="/reservations">
            <c:param name="id" value="${myid}" />
        </c:url>
    </c:otherwise>
</c:choose>
<c:url var="url2" value="/cars" />
<c:url var="url3" value="/customers/userProfile" />
<c:url var="url4" value="/login/form?logout" />

<!-- HEADER -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">Rental Car</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item active">
                <a class="nav-link" href="${url1}">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${url2}">Parco Auto</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${url3}">Profilo utente</a>
            </li>
        </ul>
    </div>
    <div class="nav navbar-nav navbar-right">
        <a href="${url4}" class="navbar-brand"><button type="button" class="btn btn-danger"><i class="bi bi-box-arrow-right"></i> Esci</button></a>
    </div>
</nav>


</body>
</html>
