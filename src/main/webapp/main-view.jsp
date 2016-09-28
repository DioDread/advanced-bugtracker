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
                                Title
                            </th>
                            <th>
                                Reporter
                            </th>
                            <th>
                                Date
                            </th>
                        </tr>
                        <c:forEach items="${reports}" var="report">
                            <tr bug-id="<c:out value="${report.bugReportId}" />" class="bug-report-record">
                                <td><c:out value="${report.name}" /></td>
                                <td><c:out value="${report.reporter}" /></td>
                                <td><c:out value="${report.dateReported}" /></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
            <div class="main-panel">
                <div class="reports-wrapper">
                    <form name="bug-report-details">
                        <h2 class="bug-report-title">Bug details.</h2>
                        <h4>Details</h4>
                        <div class="bug-details">
                            <p class="date-reported"></p>
                            <input type="date" name="dateReported" style="display: none;"> 
                            <p class="date-resolved"></p>
                            <input type="date" name="dateResolved" style="display: none;">
                            <p class="date-updated"></p>
                            <input type="date" name="dateUpdated" style="display: none;">
                            <p class="reporter"></p>
                            <input type="date" name="reporter" style="display: none;">
                            <p class="labesl"></p>
                            <p class="priority"></p>
                            <p class="state"></p>
                            <p class="project"></p>
                        </div>
                        <h4>Description</h4>
                        <p class="bug-description"></p>
                        <textarea class="bug-description-edit" style="display: none;"></textarea>
                        <h4>Comments</h4>
                    </form>
                </div>
            </div>
        </div>
        <script src="<c:url value="/static/app/utils/polyfill.js"/>"></script>
        <script src="<c:url value="/static/app/utils/utils.js"/>"></script>
        <script src="<c:url value="/static/app/utils/ajax.js"/>"></script>
        <script src="<c:url value="/static/app/main.js"/>"></script>
    </body>
</html>
