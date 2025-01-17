<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="logic.bean.GeneralUserBean, logic.utils.GoogleMapBoundary" %>

<%
	String resString = "Add Music Event";
	
	String res = (String) request.getAttribute("result");
	if(res != null){
		if (res.equals("added")) {
			resString = "Music Event Added";
		} else if (res.equals("notAdded")) {
			resString = "Failed to add music event";
		} else {
			resString = res;
		}
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
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
    <link href="./css/style.css" rel="stylesheet" type="text/css">

<!-- API for autocomplete search -->
<script type="text/javascript" src="<%="https://maps.googleapis.com/maps/api/js?key=" + GoogleMapBoundary.getAPI() + "&libraries=places"%>"></script>
</head>
<body class = "defaultBackgorund">
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
    <li><form action="artistHome.jsp" method="POST"><input type="submit" class = "selected" value="Add Music Event"></form></li>
    <li><form action="addNews.jsp" method="POST"><input type="submit" class = "notSelected" value="Add News"></form></li>
    <li><form action="LogoutServlet" method="POST"><input type="submit" class = "notSelected" value="Logout"></form></li>
    </ul>
  </div>
</div>
<div class="splitBackground right">
  <div class="centered">
  
<%
GeneralUserBean gu = (GeneralUserBean) session.getAttribute("user");
%>
<h1><i>Welcome <%= gu.getUsername() %></i></h1>
<!-- Per caricare immagine aggiungere enctype='multipart/form-data' -->
<form action = "AddMusicEventServlet" method = "POST" id = "content" enctype='multipart/form-data'>
  <div class="form-group col-md-3 col-md-offset-3" style = "width:500px; border-width:2px; border-style:solid; border-color:#b0b0b0; border-radius: 10px;">
    <div class = "form.group"><br>
    <label><i><%=resString %></i></label><br>
    <input type="text" class="form-control" name="name" placeholder="Name"><br>
    <input type="text" class="form-control" name="location" id = "location" placeholder="Location"><br>
    <input type="text" class="form-control" name="ticketone" placeholder="TicketOne Link"><br>
    <input type="date" class="form-control" name="date"><br>
    <label for="avatar"><i>Choose a cover picture:</i></label>
	<input type="file" id="file" name="file" accept="image/png, image/jpeg"><br>
    <input name="add" type="submit" value="Create Music Event" class="btn btn-info buttonColor"><br>
</div>
</div>
</form>
</div>
</div>
</div>
<!-- Autocomplete script -->
    <script>
      var input = document.getElementById('location');
      var autocomplete = new google.maps.places.Autocomplete(input);
    </script>
</body>
</html>