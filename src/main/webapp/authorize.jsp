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
            <form id="login-form" autocomplete="off">
                <div class="login-block left">
                    <label for="login">Login:</label>
                    <label for="login">Password:</label>
                </div>
                <div class="login-block right">
                    <input type="text" name="username" placeholder="Input username" required maxlength="18" minlength="8">
                    <input type="password" name="password" placeholder="Input password" required maxlength="18" minlength="8">
                </div>
                <div class="login-control">
                    <input type="button" id="sign-in" value="Sign In">
                    <input type="button" id="sign-up" value="Sign Up">
                </div>
            </form>
        </div>
        <div class="register-form-cont hide">
            <div class="header-bar">
                <span class="sub-header">Registration Form</span>
            </div>
            <form id="register-form">
                <div class="login-block left">
                    <label for="login">Login:</label>
                    <label for="login">Password:</label>
                    <label for="login">Confirm Password:</label>
                </div>
                <div class="register-block">                  
                    <input type="text" name="reg-username" placeholder="Input username">
                    <input type="password" name="reg-password" placeholder="Input password">
                    <input type="password" name="reg-confirm-password" placeholder="Confirm password">
                    <div class="register-btn-group">
                        <input type="button" id="registrate" value="Confirm">
                        <input type="button" id="reg-cancel" value="Cancel">
                    </div>
                </div>
            </form>
        </div>
        <script src="<c:url value="/static/app/utils/polyfill.js"/>"></script>
        <script src="<c:url value="/static/app/utils/utils.js"/>"></script>
        <script src="<c:url value="/static/app/utils/ajax.js"/>"></script>
        <script src="<c:url value="/static/app/auth.js"/>"></script>
    </body>
</html>
