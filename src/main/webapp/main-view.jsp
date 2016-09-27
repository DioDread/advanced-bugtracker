<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
        <link rel="stylesheet" href="<c:url value="/static/main.css"/>">
        <title>Advanced Bugtracker</title>
    </head>
    <body>
        <div class="main-viewport">
            <header class="header">
                <h1>Advanced Bugtracker:</h1>
                <span class="header-message">Hello, today we have {0} unresolved bugs to do!</span>
                <button class="report-a-bug">Report a Bug</button>
            </header>
            <div class="side-panel">
                <div class="reports-wrapper">
                    <h3>List of available bugs.</h3>
                    <div class="filter-block">
                        <label>
                            Filter bug by state:
                        </label>
                        <select>
                            <option>
                                All
                            </option>
                        </select>
                    </div>
                    <hr/>
                    <table class="bugreports">
                        <tr>
                            <th>
                                Name
                            </th>
                            <th>
                                Reporter
                            </th>
                            <th>
                                Date
                            </th>
                        </tr>
                        <c:forEach items="${reports}" var="report">
                            <tr>
                                <td><a class="bug-link" href="<c:url value="/details?id=${report.bugReportId}" />"><c:out value="${report.name}" /></a></td>
                                <td><c:out value="${report.reporter}" /></td>
                                <td><c:out value="${report.dateReported}" /></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
            <div class="main-panel">
                <div class="reports-wrapper">
                    <h3>Bug details.</h3>
                </div>
            </div>
        </div>
    </body>
</html>
