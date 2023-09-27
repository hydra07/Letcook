<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <title>Let's Cook</title>
        <meta content="" name="description">
        <meta content="" name="keywords">

        <!-- Favicons -->
        <link href="assets/img/favicon.png" rel="icon">
        <link href="assets/img/apple-touch-icon.png" rel="apple-touch-icon">

        <!-- Google Fonts -->
        <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Raleway:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

        <!-- Vendor CSS Files -->
        <link href="assets/vendor/aos/aos.css" rel="stylesheet">
        <link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
        <link href="assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
        <link href="assets/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
        <link href="assets/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">

        <!-- Template Main CSS File -->
        <link href="assets/css/style.css" rel="stylesheet">
    </head>
    <body>
        <!-- ======= Mobile nav toggle button ======= -->
        <i class="bi bi-list mobile-nav-toggle d-lg-none"></i>
        <!-- ======= Header ======= -->
        <header id="header" class="d-flex flex-column justify-content-center">
            <nav id="navbar" class="navbar nav-menu">
                <ul>
                    <li><a href="#info" class="nav-link scrollto active"><i class="bx bx-home"></i> <span>Home</span></a></li>
                    <li><a href="#about" class="nav-link scrollto"><i class="bx bx-user"></i> <span>About</span></a></li>
                    <li><a href="#search" class="nav-link scrollto"><i class="bx bx-search-alt"></i> <span>Search</span></a></li>
                    <li><a href="#portfolio" class="nav-link scrollto"><i class="bx bx-book-content"></i> <span>Portfolio</span></a></li>
                    <li><a href="#services" class="nav-link scrollto"><i class="bx bx-server"></i> <span>Services</span></a></li>
                    <c:choose>
                        <c:when test="${empty sessionScope.user}">
                            <li><a href="login" class="nav-link scrollto"><i class="bx bx-log-in"></i> <span>Đăng nhập</span></a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="logout" class="nav-link scrollto"><i class="bx bx-log-out"></i> <span>Đăng xuất</span></a></li>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${sessionScope.user.isAdmin}">
                    <li><a href="admin_dashboard.jsp" class="nav-link scrollto"><i class="bx bx-key"></i> <span>Trang Admin</span></a></li>
                    </c:if>        
                </ul>
            </nav><!-- .nav-menu -->
        </header><!-- End Header -->

        <!-- ======= Infor ======= -->
        <section id="info" class="d-flex flex-column justify-content-center">
            <div class="container" data-aos="zoom-in" data-aos-delay="100">
                <h1>Let's Cook</h1>
                <p><span class="typed" data-typed-items="Khám phá ẩm thực., Kết nối cộng đồng."></span></p>
                <a href="${pageContext.request.contextPath}/login" class="btn btn-primary" id="button-started">Started <i class="bx bi-arrow-right"></i></a>
            </div>
        </section><!-- End Info -->

        <!-- ======= Search Section ======= -->
        <section id="search" class="d-flex-column justify-content-center">
            <div class="container" data-aos="zoom-in" data-aos-delay="100">
                <h1>Search</h1>
                <form action="">
                    <div class="input-group">
                        <input type="text" class="form-control" name="search" id="search-input" placeholder="Search...">
                        <button class="btn btn-primary" type="submit" id="search-submit"><i class="bx bx-right-arrow-alt"></i></button>
                    </div>
                </form>
            </div>
        </section><!-- End Search Section -->
        <div id="preloader"></div>
        <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

        <!-- Vendor JS Files -->
        <script src="assets/vendor/purecounter/purecounter_vanilla.js"></script>
        <script src="assets/vendor/aos/aos.js"></script>
        <script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="assets/vendor/glightbox/js/glightbox.min.js"></script>
        <script src="assets/vendor/isotope-layout/isotope.pkgd.min.js"></script>
        <script src="assets/vendor/swiper/swiper-bundle.min.js"></script>
        <script src="assets/vendor/typed.js/typed.umd.js"></script>
        <script src="assets/vendor/waypoints/noframework.waypoints.js"></script>
        <script src="assets/vendor/php-email-form/validate.js"></script>

        <!-- Template Main JS File -->
        <script src="assets/js/main.js"></script>
    </body>
</html>