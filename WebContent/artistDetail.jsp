<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="logic.buyticket.*, logic.bean.ArtistBean" %>

<%
	String foll;
	String origin = (String) session.getAttribute("origin");
	String role = (String) session.getAttribute("userRole");
	if(role == null){
		role = "";
	}
	ArtistBean ab = (ArtistBean) session.getAttribute("artist");
	boolean isFoll = (boolean) request.getAttribute("isFoll");
	
	if (isFoll == false) {
		foll = "Follow";
	} else {
		foll = "Unfollow";
	}
%>

<!DOCTYPE html>
<html>
<meta charset="ISO-8859-1">
    <!-- info about content, e.g.: content type, keywords, charset or description -->
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <!-- linked CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- Logo icon -->
    <link rel="icon" href="img/concertIcon.png">
    <!-- 
    <link href="css/bootstrap-theme.css" rel="stylesheet">
    <link href="css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="css/bootstrap.css" rel="stylesheet">
     -->
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <!-- 
    <script src="js/bootstrap.bundle.js"></script>
    <script src="js/bootstrap.js"></script>
    <script src="js/bootstrap.bundle.min.js"></script>
 -->

    <link href="./css/style.css" rel="stylesheet" type="text/css">

<title>Artist Details</title>
</head>
<body>
<div class="container">

 <div class="splitBanner left">
  <div class="centered">
  
  <!-- Logo -->
  <div class = "logoPosition">
  <img class = "logoImg" src="img/concertIcon.png" height = 106.8 width = 106.8>
  <div class = "row">
  <p class = "logoText">LIVE<mark style = "background-color: rgba(0,0,0,0); color: #6A4C93">the</mark>MUSIC</p>
  </div>
  </div>
  
   <ul>
    <%
    if(origin.equals("AdminMusicEventServlet") || role.equals("admin")){
    	%>
    	<li><form action="AdminMusicEventServlet" method="POST"><input type="submit" class = "selected" value="Home"></form></li>
    	<li><form action="ReadNewsServlet" method="POST"><input type="submit" class = "notSelected" value="News"></form></li>
    	<%
    } else {
    	%>
    
    <%
    if(origin.equals("BuyTicketServlet")){
    	%>
    	<li><form action="BuyTicketServlet" method="POST"><input type="submit" class = "selected" value="Home"></form></li>
    	<%
    } else {
    	%>
    	<li><form action="BuyTicketServlet" method="POST"><input type="submit" class = "notSelected" value="Home"></form></li>
    <%} %>
    <li><form action="ReadNewsServlet" method="POST"><input type="submit" class = "notSelected" value="News"></form></li>
    <li><form action="favorites.jsp" method="POST"><input type="submit" class = "notSelected" value="Favorites"></form></li>
    <li><form action="FriendsServlet" method="POST"><input type="submit" class = "notSelected" value="Friends"></form></li>
    <li><form action="aroundyou.jsp" method="POST"><input type="submit" class = "notSelected" value="Around you"></form></li>
    <%
    if(origin.equals("YourEventsServlet")){
    	%>
    	<li><form action="YourEventsServlet" method="POST"><input type="submit" class = "selected" value="Your Events"></form></li>
    	<%
    } else {
    	%>
    	<li><form action="YourEventsServlet" method="POST"><input type="submit" class = "notSelected" value="Your Events"></form></li>
    <%} %>
    <%} %>
    <li><form action="LogoutServlet" method="POST"><input type="submit" class = "notSelected" value="Logout"></form></li>
    </ul>
  </div>
</div>

<div class="splitBackground right">
<img src = "<%="img/profilePictures/" + ab.getProfilePicture() %>" height = 334  width = 1252 style = "object-fit: cover;" >
  <form action = "ButtonHandler" method = "POST">
<input type="submit" class = "submit" name = "back" value = "back">
</form>
  <div class="centered" style="margin-left:30px;">
	<h1><%=ab.getBandName()%></h1>
  </div>
  <%
  if(!origin.equals("AdminMusicEventServlet") || role.equals("admin")){
  %>
  <form action = "ButtonHandler" method = "POST">
  <input type = "submit" name = "follow" value = "<%=foll%>">
  <input type = "hidden" name = "artist" value = "<%=ab.getUsername() %>">
  </form>
  <%} %>
  </div>
  </div>

</body>
</html>