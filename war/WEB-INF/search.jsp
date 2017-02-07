<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<!-- Always shows a header, even in smaller screens. -->
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
  <header class="mdl-layout__header">
    <div class="mdl-layout__header-row">
      <!-- Title -->
      <span class="mdl-layout-title">Search</span>
      <!-- Add spacer, to align navigation to the right -->
      <div class="mdl-layout-spacer"></div>
      <!-- Navigation. We hide it in small screens. -->
      <nav class="mdl-navigation mdl-layout--large-screen-only">
        <a class="mdl-navigation__link" href="${accountUrl}">My Account</a>
        <a class="mdl-navigation__link" href="/search">Search</a>
        <a class="mdl-navigation__link" href="/upload">Upload</a>
        <a class="mdl-navigation__link" href="/login">Log Out</a>
      </nav>
    </div>
  </header>
  <div class="mdl-layout__drawer">
    <span class="mdl-layout-title">Search</span>
    <nav class="mdl-navigation">
      <a class="mdl-navigation__link" href="${accountUrl}">My Account</a>
        <a class="mdl-navigation__link" href="/search">Search</a>
        <a class="mdl-navigation__link" href="/upload">Upload</a>
        <a class="mdl-navigation__link" href="/login">Log Out</a>
    </nav>
  </div>
  	
	<style>
			.search{
				margin-top: 50px;
				margin-left: 100px;
				margin-right: 100px;
			}
	</style>
			<div class="search">
			<form id="f1" action="search" method="post">
				<div class="mdl-textfield mdl-js-textfield">
		   			<input class="mdl-textfield__input" size="200" type="text" name="keyword" id="key">
					<label class="mdl-textfield__label" for="key">Enter Keyword...</label>
		  		</div>
			  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" id="search" value="search" size="200" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored"><br>
		  		<div>
					<input type="radio" name="id" value="all" checked>All
					<input type="radio" name="id" value="Student">Student
					<input type="radio" name="id" value="Instructor" >Instructor<br>
		  		</div>
	
				<div>
					<input type="radio" name="filter" value="all" checked>All
					<input type="radio" name="filter" value="name" >File Name
					<input type="radio" name="filter" value="content">Content
					<input type="radio" name="filter" value="uploader" >Uploader
					<input type="radio" name="filter" value="course" >Course<br>
				</div><br>
			</form>	
		
		<c:if test="${not empty error}">
    		<p name="error">${error}</p>
    		</c:if>
	   	 <p class="result"">${text}</p>
		</div>
		
	<head>
<meta charset="utf-8">
	<meta name="google-signin-scope" content="profile email">
    	<meta name="google-signin-client_id" content="YOUR_CLIENT_ID.apps.googleusercontent.com">
    	<script src="https://apis.google.com/js/platform.js" async defer></script>
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	<link rel="stylesheet" href="https://code.getmdl.io/1.1.1/material.indigo-pink.min.css">
	<script defer src="https://code.getmdl.io/1.1.1/material.min.js"></script>
	<title>Search</title>
	</head>

	<body>


	
	
	</body>
	
<html>