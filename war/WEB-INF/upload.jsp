<!DOCTYPE html>
<html>
	<!-- Always shows a header, even in smaller screens. -->
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
  <header class="mdl-layout__header">
    <div class="mdl-layout__header-row">
      <!-- Title -->
      <span class="mdl-layout-title">Upload</span>
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
    <span class="mdl-layout-title">Upload</span>
    <nav class="mdl-navigation">
	<a class="mdl-navigation__link" href="${accountUrl}">My Account</a>
        <a class="mdl-navigation__link" href="/search">Search</a>
        <a class="mdl-navigation__link" href="/upload">Upload</a>
        <a class="mdl-navigation__link" href="/login">Log Out</a>
    </nav>
  </div>
  <main class="mdl-layout__content"></br></br>
    <div class="page-content"><div class ="signup" >
		<form action="${uploadUrl}" method="post" enctype="multipart/form-data">
			Upload files(.pdf,.html,.txt) with maximum size 5MB each. You can tag the file with a course number while uploading.<br><br>
		<input type="file" id="fileUpload" name="file" class="mdl-button mdl-js-button mdl-button--accent" multiple><br>
		<div class="mdl-textfield mdl-js-textfield">
			<input class="mdl-textfield__input" type="text" name="course" id="course">
			<label class="mdl-textfield__label" for="course">Course Number (e.g. CSC301)...</label>
  		</div><br>
	  	<input type="submit" id="upload" value="Upload" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored"><br>
		</form>	
		<c:if test="${not empty error}">
    		<p class="text">${text}</p>
    	</c:if>
	</div></div>	
  </main>
</div>
	<head>
	<meta charset="utf-8">
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	<link rel="stylesheet" href="https://code.getmdl.io/1.1.1/material.indigo-pink.min.css">
	<script defer src="https://code.getmdl.io/1.1.1/material.min.js"></script>
	<title>Upload</title>
	
	</head>
	<style>
	.mdl-layout__content{text-align: center;margin-top:50px}
	</style>
	<body>
	
	<style>	</style>
	
	</body>
<html>
