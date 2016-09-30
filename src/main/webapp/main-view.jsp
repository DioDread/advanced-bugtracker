<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
        <link rel="stylesheet" href="<c:url value="/static/main.css"/>">
        <link rel="icon" type="image/png" href="<c:url value="/static/favicon.png"/>" />
        <title>Advanced Bugtracker</title>
    </head>
    <body>
        <div class="main-viewport">
            <header class="header">
                <h1>Advanced Bugtracker:</h1>
                <span class="header-message">Hello, today we have {0} unresolved bugs to do!</span>
                <button class="btn-lg report-a-bug">Report a Bug</button>
                <button class="btn-lg show-stats">Statistics</button>
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
                            <p class="date-resolved"></p>
                            <p class="date-updated"></p>
                            <p class="reporter"></p>
                            <p class="desired-resolution-dat"></p>
                            <input type="date" name="desiredResolutionDate" style="display: none;">
                            <input type="text" name="reporter" style="display: none;">
                            <p class="labesl"></p>
                            <!--TODO: Create valid labels component, which will be display cheaps, add and delete them-->
                            <p class="priority"></p>
                            <select name="priority" style="display: none;">
                                <option value="">Unspecified</option>
                            </select>
                            <p class="state"></p>
                            <select name="state" style="display: none;">
                                <option value="">Unspecified</option>
                            </select>
                            <p class="project"></p>
                            <select name="project" style="display: none;">
                                <option value="">Unspecified</option>
                            </select>
                        </div>
                        <h4>Description</h4>
                        <p class="bug-description"></p>
                        <textarea class="bug-description-edit" style="display: none;"></textarea>
                        <h4>Comments</h4>
                    </form>
                </div>
            </div>
        </div>

        <div class="new-bug-report-dialog" style="display: none;">
            <h3 class="new-bug-report-dialog-title">Report a Bug.<div class="dialog-close-btn">X</div></h3>
            <div class="dialog-cont">
                <form name="new-bug-report">
                    <label for="name">Title:</label>
                    <input type="text" name="name">
                    <label for="desiredResolutionDate">Desired Resolution Date</label>
                    <input type="date" name="desiredResolutionDate">
                    <label for="description">Describe your problem</label>
                    <textarea name="description" class="dialog-bug-description"></textarea>
                    <label for="reporter">Your Name</label>
                    <input type="text" name="reporter">
                    <p class="labesl">Labels</p>
                    <label for="priority">Select Priority</label>
                    <select name="priority">
                        <option value="">Unspecified</option>
                    </select>
                    <label for="project">Select Project</label>
                    <select name="project">
                        <option value="">Unspecified</option>
                    </select>
                    <input type="submit" value="Submit" class="btn-md">
                </form>
            </div>
        </div>

        <script src="<c:url value="/static/app/utils/polyfill.js"/>"></script>
        <script src="<c:url value="/static/app/utils/utils.js"/>"></script>
        <script src="<c:url value="/static/app/utils/ajax.js"/>"></script>
        <script src="<c:url value="/static/app/main.js"/>"></script>
    </body>
</html>
