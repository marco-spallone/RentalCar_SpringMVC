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
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <a class="navbar-brand" href="#">Rental Car</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNav">
    <ul class="navbar-nav">
    </ul>
  </div>
  <div class="nav navbar-nav navbar-right">
  </div>
</nav>

<div class="container">
  <div class="row">
    <div class="mx-auto mt-3 mb-3 login-form col-sm-6 col-md-6">
      <c:url var="loginUrl" value="/login" />
      <form action="${loginUrl}" method="post">
        <c:if test="${param.error!=null}">
          <div class="alert alert-danger">
            <p>Username o password errati!</p>
          </div>
        </c:if>
        <c:if test="${param.forbidden!=null}">
          <div class="alert alert-danger">
            <p>Accesso non autorizzato!</p>
          </div>
        </c:if>
        <c:if test="${param.logout!=null}">
          <div class="alert alert-danger">
            <p>Logout eseguito!</p>
          </div>
        </c:if>

        <div class="input-group input-sm">
          <label class="input-group-addon" for="username"><i class="fa fa-user"></i></label>
          <input type="text" class="form-control" id="username" name="username" placeholder="Username" required>
        </div>
        <div class="input-group input-sm">
          <label class="input-group-addon" for="password"><i class="fa fa-user"></i></label>
          <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
        </div>
        <div class="form-actions">
          <input type="submit" class="btn- btn-block btn-success btn-default" value="LOGIN" />
        </div>
      </form>
    </div>
  </div>
</div>



</body>
</html>
