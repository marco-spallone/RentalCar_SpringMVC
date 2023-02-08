<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<head>
    <title>Customers</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
    <script src="https://kit.fontawesome.com/6b1574191b.js" crossorigin="anonymous"></script>
</head>
<jsp:include page="navbar.jsp">
    <jsp:param name="url1" value="customers" />
    <jsp:param name="url2" value="cars"/>
    <jsp:param name="url3" value="userProfile"/>
    <jsp:param name="url4" value="login/form"/>
</jsp:include>
<div class="container">
    <div class="row">
        <div class="mx-auto mt-5 col-md-6 col-sm-6">
            <h3 class="page-title">Customers</h3>
            <div class="mt-4 mb-4"><a href="<spring:url value="/addCustomer" />">
                <button class="btn btn-outline-info"><i class="fa-solid fa-user-plus fa-lg" ></i> Aggiungi customer</button></a></div>
            <div class="form-horizontal"><div class="form-group mt-2 mb-2">
                <form method="post" action="filterCustomers">
                    <select name="field" class="form-select">
                        <option value="nome">Nome</option>
                        <option value="cognome">Cognome</option>
                    </select>
                    <input name="value" id="value" type="text" class="form-control mt-2" placeholder="Filtra per..."/>
                    <button type="submit" class="mt-3 btn btn-outline-primary"><i class="fa-solid fa-filter"></i></button>
                    <a href="customers"><button type="button" class="mt-3 btn btn-outline-danger"><i class="fa-solid fa-x"></i></button></a>
                </form>
            </div></div>
            <div id="tabUtenti">
                <table class="table table-striped table-bordered" id="tab">
                    <thead>
                    <tr>
                        <th scope="col">Nome</th>
                        <th scope="col">Cognome</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="customer" items="${customers}">
                        <tr>
                            <td>${customer.nome}</td>
                            <td>${customer.cognome}</td>
                            <td><a href="<spring:url value='/editCustomer?id=${customer.idUtente}' />"><button class="mx-auto btn btn-outline-warning"><i class="fa-sharp fa-solid fa-pen fa-lg"></i> Modifica</button></a></td>
                            <td><a href="<spring:url value='/viewReservations?id=${customer.idUtente}' />"><button class="btn btn-outline-info"><i class="fa-solid fa-calendar fa-lg"></i> Prenotazioni</button></a></td>
                            <td><a href="<spring:url value='/deleteCustomer?id=${customer.idUtente}' />"><button class="btn btn-outline-danger"><i class="fa-solid fa-trash fa-lg"></i> Elimina</button></a></td>
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
