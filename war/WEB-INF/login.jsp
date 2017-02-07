<!DOCTYPE html>
<html>
	<!-- Always shows a header, even in smaller screens. -->
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
  <header class="mdl-layout__header">
    <div class="mdl-layout__header-row">
      <!-- Title -->
      <span class="mdl-layout-title">Login</span>
      <!-- Add spacer, to align navigation to the right -->
      <div class="mdl-layout-spacer"></div>
      <!-- Navigation. We hide it in small screens. -->
      <nav class="mdl-navigation mdl-layout--large-screen-only">
      <a class="mdl-navigation__link" href="/login">Login</a>
      <a class="mdl-navigation__link" href="/signup">Signup</a>
        <a class="mdl-navigation__link" href="/help">Help</a>
      </nav>
    </div>
  </header>
  <div class="mdl-layout__drawer">
    <span class="mdl-layout-title">Login</span>
    <nav class="mdl-navigation">
      <a class="mdl-navigation__link" href="/login">Login</a>
      <a class="mdl-navigation__link" href="/signup">Signup</a>
        <a class="mdl-navigation__link" href="/help">Help</a>
    </nav>
  </div>
  <main class="mdl-layout__content">
    <div class="page-content">
    	<!-- Wide card with share menu button -->
	<style>
	.demo-card-wide.mdl-card {
	  	  top:80px;
	  width: 512px;
	  margin: auto;
	}
	.demo-card-wide > .mdl-card__title {
	  color: #fff;
	  height: 176px;
	  background: url('card.jpg') center / cover;
	}
	.demo-card-wide > .mdl-card__menu {
	  color: #fff;
	}
	</style>

	<div class="demo-card-wide mdl-card mdl-shadow--2dp">
	  <div class="mdl-card__title">
	    <h2 class="mdl-card__title-text">Welcome to UTM Search Engine</h2>
	  </div>
	  <div class="mdl-card__supporting-text">
	    Not having an account?
	  </div>
	  <div class="mdl-card__actions mdl-card--border">
	    <a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" href="/signup">
	      Sign Up
	    </a>
	  </div>
	</div>
	<style>
			.login{
				height:10%;
				padding-top:100px;
			}
	</style>
	<div class="login" align="center">
		<form action="login" method="post">
		<div class="mdl-textfield mdl-js-textfield">
   			<input class="mdl-textfield__input" type="text" name="user" id="username">
    			<label class="mdl-textfield__label" for="username">Username...</label>
  		</div><br>
	 	<div class="mdl-textfield mdl-js-textfield">
			<input class="mdl-textfield__input" type="password" name="password" id="pass">
			<label class="mdl-textfield__label" for="pass">Password...</label>
  		</div><br>
	  	<input type="submit" id="Submit" value="Login" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored"><br>
		</form>	</br>
		<a class="recover" href="/confirmUser">Forget your password?</a></br>
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
    	<meta name="google-signin-client_id" content="505292103738-bcmrc63t026v190dkmn5ijb4eqr6anlj.apps.googleusercontent.com">
    	<script src="https://apis.google.com/js/platform.js" async defer></script>
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	<link rel="stylesheet" href="https://code.getmdl.io/1.1.1/material.indigo-pink.min.css">
	<script defer src="https://code.getmdl.io/1.1.1/material.min.js"></script>
	<title>Login</title>
	
	</head>
	<body>
	</body>
<html>