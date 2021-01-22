<%@ page import="ru.job4j.job4jcinema.persistence.Session" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<%
    Session sessionNow = new Session(1, 1,
            LocalDateTime.of(2021, 01, 23, 10, 20, 00),
            "The Best Movie 0+");
    request.setAttribute("session", sessionNow);
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="css/style.css" rel="stylesheet" type="text/css">
    <script src="js/script.js"></script>
    <title>Cinema</title>
</head>
<body>
<div id="left">
    <h1 align="center">Session: <c:out value="${session.movieTitle}"/></h1>
    <h2 align="center"><span id="nameHall"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        Beginning: <c:out value="${session.dataStartString}"/></h2>
    <div align="center" id="divScreen"><h3 align="center">Screen</h3></div>
    <div id="hall" class="hall" align="center" onclick="selectSeat(event)"></div>
</div>
<div id="right"><h2 align="center">My ticket</h2>
    <div align="center" id="divPay">
        <h4>
            <span id="selectSeatNumber">Please select your seat</span><br>
            <span id="selectSeatAmount"></span>
        </h4>
        <div>
            <input type="text" class="form-control" name="username" id="username" placeholder="Enter your Name"><br>
            <span id="username_empty" style="color: #ff0000;"></span>
        </div>
        <br>
        <div>
            <input type="tel" class="form-control" name="phone" id="phone" placeholder="Enter your Phone"><br>
            <span id="phone_empty" style="color: #ff0000;"></span>
        </div>
        <br>
        <input type="hidden" class="form-control" name="row" id="row" value="">
        <input type="hidden" class="form-control" name="place" id="place" value="">
        <input type="hidden" class="form-control" name="price" id="price" value="">
        <button type="submit" onclick="submitPay()">Pay</button>
    </div>
</div>
</body>
</html>