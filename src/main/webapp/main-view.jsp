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
                    <button class="btn-lg edit-a-bug">Edit Bug Report</button>
                    <form name="bug-report-details">
                        <span class="editable-control" control-type="input">
                            <h2 class="bug-report-title data">Bug details.</h2>
                            <input type="text" name="name" class="title-input" placeholder="Enter subject" style="display: none;">
                        </span>
                        <h3>Details</h3>
                        <div class="bug-details">
                            <p><span class="detail-label">Reported at: </span><span class="date-reported"></span></p>
                            <p><span class="detail-label">Resolution date: </span><span class="date-resolved"></span></p>
                            <p><span class="detail-label">Last updated at: </span><span class="date-updated"></span></p>
                            <p class="editable-control" control-type="input">
                                <span class="detail-label">Reported by: </span><span class="reporter data"></span>
                                <input type="text" name="reporter" style="display: none;">
                            </p>
                            <p class="editable-control" control-type="input">
                                <span class="detail-label">Desired resolution date: </span><span class="desired-resolution-date data"></span>
                                <input type="date" name="desiredResolutionDate" style="display: none;">
                            </p>
                            <p class="editable-control" control-type="select">
                                <span class="detail-label">Priority: </span><span class="priority data"></span>
                                <select name="priority" style="display: none;">
                                    <option value="0">Unspecified</option>
                                    <option value="1">Minor</option>
                                    <option value="2">Medium</option>
                                    <option value="3">Major</option>
                                    <option value="4">Critical</option>
                                    <option value="5">Blocker</option>
                                </select>
                            </p>
                            <p class="editable-control" control-type="select">
                                <span class="detail-label">State: </span><span class="state data"></span>
                                <select name="state" style="display: none;">
                                    <option value="">Unspecified</option>
                                </select>
                            </p>
                            <p class="editable-control" control-type="select">
                                <span class="detail-label">Related to project: </span><span class="project data"></span>
                                <select name="project" style="display: none;">
                                    <option value="">Unspecified</option>
                                </select>
                            </p>
                        </div>
                        <h5>Labels:</h5>
                        <div class="label-creator editable-control" control-type="colorpicker" style="display: none;">
                            <input type="text" name="label-name" placeholder="Input label name">
                            <input type="color" name="label-color" value="#07b3eb" title="Select Label Color">
                            <input type="button" name="add-label" value="+" disabled>
                            <input type="hidden" name="labels-data" >
                        </div>
                        <div class="labels-area data"></div>
                        <h3>Description</h3>
                        <p class="editable-control" control-type="textarea">
                            <span class="bug-description data"></span>
                            <textarea class="bug-description-edit" style="display: none;"></textarea>
                        </p>
                        <h3>Comments</h3>
                        <p class="editable-control" control-type="input">
                            <input type="submit" class="btn-md update-a-bug" value="Update Bug Report" style="display: none;">
                        </p>
                    </form>
                </div>
            </div>
        </div>

        <div class="new-bug-report-dialog" draggable="true" style="display: none;">
            <h3 class="new-bug-report-dialog-title">Report a Bug.<div class="dialog-close-btn">X</div></h3>
            <div class="dialog-cont">
                <form name="new-bug-report">
                    <label for="name">Title:</label>
                    <input type="text" name="name" placeholder="Enter subject">
                    <label for="desiredResolutionDate">Desired Resolution Date: </label>
                    <input type="date" name="desiredResolutionDate">
                    <label for="description">Describe your problem: </label>
                    <textarea name="description" class="dialog-bug-description"></textarea>
                    <label for="reporter">Your Name: </label>
                    <input type="text" name="reporter" placeholder="Enter your name">
                    <div class="label-creator">
                        <label class="labesl" for="label-name">Labels:</label>
                        <input type="text" name="label-name" placeholder="Input label name">
                        <input type="color" name="label-color" value="#07b3eb" title="Select Label Color">
                        <input type="button" name="add-label" value="+" disabled>
                        <div class="labels-area"></div>
                        <input type="hidden" name="labels-data" >
                    </div>
                    <label for="priority">Select Priority:</label>
                    <select name="priority">
                        <option value="0">Unspecified</option>
                        <option value="1">Minor</option>
                        <option value="2">Medium</option>
                        <option value="3">Major</option>
                        <option value="4">Critical</option>
                        <option value="5">Blocker</option>
                    </select>
                    <label for="project">Select Project:</label>
                    <select name="project">
                        <option value="">Unspecified</option>
                    </select>
                    <div class="submit-a-bug-cont">
                        <input type="submit" value="Submit" class="btn-md submit-a-bug">
                    </div>
                </form>
            </div>
        </div>

        <script src="<c:url value="/static/app/utils/polyfill.js"/>"></script>
        <script src="<c:url value="/static/app/utils/utils.js"/>"></script>
        <script src="<c:url value="/static/app/utils/ajax.js"/>"></script>
        <script src="<c:url value="/static/app/main.js"/>"></script>
    </body>
</html>
