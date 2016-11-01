<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login to Advanced Bug Tracker.</title>
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
        <link href="<c:url value="/static/res/font-awesome/css/font-awesome.css"/>" rel="stylesheet">
        <link href="<c:url value="/static/res/login-form.css"/>" rel="stylesheet">
        <link href="<c:url value="/static/res/gradient-back.css"/>" rel="stylesheet">
    </head>
    <body class="gradient-back">
        
        <div class="title-bar">
            <span class="header">Advanced Bug Tracker</span>
        </div>
        <div class="login-form-cont">
            <form id="login-form">
                <div class="login-block left">
                    <label for="login">Login:</label>
                    <label for="login">Password:</label>
                </div>
                <div class="login-block right">                  
                    <input type="text" name="login" placeholder="Input username">
                    <input type="password" name="password" placeholder="Input username">
                </div>
                <div class="login-control">
                    <input type="button" id="sign-in" value="Sign In">
                    <input type="button" id="sign-up" value="Sign Up">
                </div>
            </form>
        </div>
    </body>
</html>
