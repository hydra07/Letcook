<%--
  Created by IntelliJ IDEA.
  User: xuant
  Date: 9/25/2023
  Time: 4:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>Register</title>
        <link href="assets/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">
        <!-- Custom styles for this template-->
        <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <section class="vh-100" style="background-color: #eee;">
            <div class="container-fluid h-custom">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-lg-12 col-xl-11">
                        <div class="card text-black" style="border-radius: 25px;">
                            <div class="card-body p-md-5">
                                <div class="row justify-content-center">
                                    <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">
                                        <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Sign up</p>                                       
                                        <c:if test="${not empty mailError}">
                                            <p class="alert alert-danger"><c:out value="${mailError}" /></p>
                                        </c:if>
                                        <c:if test="${not empty error}">
                                            <p class="alert alert-danger"><c:out value="${error}" /></p>
                                        </c:if>
                                        <c:if test="${not empty success}">
                                            <p class="alert alert-success"><c:out value="${success}" /></p>
                                        </c:if>
                                        <c:if test="${not empty passwordError}">
                                            <p class="alert alert-danger"><c:out value="${passwordError}" /></p>
                                        </c:if>
                                        <form class="mx-1 mx-md-4" method="post" action="register">
                                            <div class="d-flex flex-row align-items-center mb-4">
                                                <i class="fas fa-user fa-lg me-3 fa-fw"></i>
                                                <div class="form-outline flex-fill mb-0">
                                                    <input type="text" id="name" class="form-control" name="name" required/>
                                                    <label class="form-label" for="name">Your Name</label>
                                                    <c:if test="${not empty nameError}">
                                                        <div class="alert alert-danger">
                                                            <c:out value="${nameError}" />
                                                        </div>
                                                    </c:if>
                                                </div>
                                            </div>

                                            <div class="d-flex flex-row align-items-center mb-4">
                                                <i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
                                                <div class="form-outline flex-fill mb-0">
                                                    <input type="email" id="email" class="form-control" name="email" required/>
                                                    <label class="form-label" for="email">Your Email</label>
                                                </div>
                                            </div>

                                            <div class="d-flex flex-row align-items-center mb-4">
                                                <i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
                                                <div class="form-outline flex-fill mb-0">
                                                    <input type="text" id="phone" class="form-control" name="phone" required/>
                                                    <label class="form-label" for="phone">Your Phone</label>
                                                    <c:if test="${not empty phoneError}">
                                                        <div class="alert alert-danger">
                                                            <c:out value="${phoneError}" />
                                                        </div>
                                                    </c:if>
                                                </div>
                                            </div>

                                            <div class="d-flex flex-row align-items-center mb-4">
                                                <i class="fas fa-phone fa-lg me-3 fa-fw"></i>
                                                <div class="form-outline flex-fill mb-0">
                                                    <input type="number" id="password" class="form-control" name="password" required/>
                                                    <label class="form-label" for="password">Password</label>
                                                    <c:if test="${not empty passwordError}">
                                                        <div class="alert alert-danger">
                                                            <c:out value="${passwordError}" />
                                                        </div>
                                                    </c:if>
                                                </div>
                                            </div>

                                            <div class="d-flex flex-row align-items-center mb-4">
                                                <i class="fas fa-key fa-lg me-3 fa-fw"></i>
                                                <div class="form-outline flex-fill mb-0">
                                                    <input type="password" id="repassword" class="form-control" name="repassword" required/>
                                                    <label class="form-label" for="repassword">Repeat your password</label>
                                                </div>
                                            </div>

                                            <div class="form-check d-flex justify-content-center mb-5">
                                                <input class="form-check-input me-2" type="checkbox" value="" id="confirm" name="confirm" required/>
                                                <label class="form-check-label" for="confirm">
                                                    I agree all statements in <a href="#!">Terms of service</a>
                                                </label>
                                            </div>

                                            <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
                                                <button type="submit" class="btn btn-primary btn-lg">Register</button>
                                            </div>
                                        </form>

                                    </div>
                                    <div class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">
                                        <img src="img/logo2.jpg"
                                             class="img-fluid" alt="Sample image">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <script src="assets/vendor/jquery/jquery.min.js"></script>
        <script src="assets/js/bootstrap.bundle.min.js"></script>
        <!-- Core plugin JavaScript-->
        <script src="assets/vendor/jquery-easing/jquery.easing.min.js"></script>
        <!-- Custom scripts for all pages-->
    </body>
</html>
