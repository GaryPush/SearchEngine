<!DOCTYPE html>
<html>
	<!-- Always shows a header, even in smaller screens. -->
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
  <header class="mdl-layout__header">
    <div class="mdl-layout__header-row">
      <!-- Title -->
      <span class="mdl-layout-title">My Account</span>
      <!-- Add spacer, to align navigation to the right -->
      <div class="mdl-layout-spacer"></div>
      <!-- Navigation. We hide it in small screens. -->
      <nav class="mdl-navigation mdl-layout--large-screen-only">
	<a class="mdl-navigation__link" href="${accountUrl}" name="myaccount">My Account</a>
        <a class="mdl-navigation__link" href="/search">Search</a>
        <a class="mdl-navigation__link" href="/upload">Upload</a>
        <a class="mdl-navigation__link" href="/login">Log Out</a>
      </nav>
    </div>
  </header>
  <div class="mdl-layout__drawer">
    <span class="mdl-layout-title">My Account</span>
    <nav class="mdl-navigation">
	<a class="mdl-navigation__link" href="${accountUrl}">My Account</a>
        <a class="mdl-navigation__link" href="/search">Search</a>
        <a class="mdl-navigation__link" href="/upload">Upload</a>
        <a class="mdl-navigation__link" href="/login">Log Out</a>
    </nav>
  </div>
  <main class="mdl-layout__content"></br></br>
    <p>${accountInfo}</p>
  </main>
</div>
	<head>
	<meta charset="utf-8">
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	<link rel="stylesheet" href="https://code.getmdl.io/1.1.1/material.indigo-pink.min.css">
	<script defer src="https://code.getmdl.io/1.1.1/material.min.js"></script>
	<title>My Account</title>
	
	</head>
	<style>
	.mdl-layout__content{margin: auto}
.demo-card-wide.mdl-card {
	  	  top:80px;
	  width: 512px;
	  margin: auto;
	}
	</style>
	<body>
	
	<style>	</style>
	
	</body>
<html>