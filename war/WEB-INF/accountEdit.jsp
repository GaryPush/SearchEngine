<!DOCTYPE html>
<html>
	<!-- Always shows a header, even in smaller screens. -->
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
  <header class="mdl-layout__header">
    <div class="mdl-layout__header-row">
      <!-- Title -->
      <span class="mdl-layout-title">Edit Account</span>
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
    <span class="mdl-layout-title">Edit Account</span>
    <nav class="mdl-navigation">
	<a class="mdl-navigation__link" href="${accountUrl}">My Account</a>
        <a class="mdl-navigation__link" href="/search">Search</a>
        <a class="mdl-navigation__link" href="/upload">Upload</a>
        <a class="mdl-navigation__link" href="/login">Log Out</a>
    </nav>
  </div>
  	<style>
			.save{
				text-align:center;
				margin-top: 50px
			}
	</style>
<div class ="save">
		<form action="${uploadUrl}" method="post" enctype="multipart/form-data">
	 	<div class="mdl-textfield mdl-js-textfield">
			<input class="mdl-textfield__input" type="text" name="fName" id="fName">
			<label class="mdl-textfield__label" for="fName">First Name...</label>
  		</div><br>
		<div class="mdl-textfield mdl-js-textfield">
		    <input class="mdl-textfield__input" type="text" name="lName" id="lName">
		    <label class="mdl-textfield__label" for="lName">Last Name...</label>
  		</div><br>
		<div class="mdl-textfield mdl-js-textfield">
		    <input class="mdl-textfield__input" type="text" name="email" id="email">
		    <label class="mdl-textfield__label" for="email">Email...</label>
  		</div><br>
			Upload a Profile Picture:<br><br>
		<input type="file" id="computerPic" name="image" class="mdl-button mdl-js-button mdl-button--accent"><br><br>

	  	<input type="submit" id="Submit" value="Save" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored"><br>
		</form>	
		<c:if test="${not empty error}">
    		<p class="error">${error}</p>
    	</c:if>
		
	</div>
  </div>
  </main>
</div>
	<head>
	<meta charset="utf-8">
	<meta name="google-signin-scope" content="profile email">
    	<meta name="google-signin-client_id" content="YOUR_CLIENT_ID.apps.googleusercontent.com">
    	<script src="https://apis.google.com/js/platform.js" async defer></script>
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	<link rel="stylesheet" href="https://code.getmdl.io/1.1.1/material.indigo-pink.min.css">
	<script defer src="https://code.getmdl.io/1.1.1/material.min.js"></script>
	<title>My Account</title>
	
	</head>
	<body>
	
	</body>
<html>