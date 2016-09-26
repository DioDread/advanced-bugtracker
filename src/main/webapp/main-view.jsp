<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<c:url value="/static/main.css"/>">
        <title>Advanced Bugtracker</title>
    </head>
    <body>
        <div class="main-viewport">
            <header class="header">
                <h1>Advanced Bugtracker</h1>
            </header>
            <div class="side-panel"></div>
            <div class="main-panel">
                <div class="reports-wrapper">
                    <h3>List of available bugs.</h3>
                    <table>
                        <c:forEach items="${reports}" var="report">
                            <tr>
                                <td><c:out value="${report.name}" /></td>
                                <td><c:out value="${report.reporter}" /></td>
                                <td><c:out value="${report.dateReported}" /></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
