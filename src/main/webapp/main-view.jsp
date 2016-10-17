<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
        <link href="<c:url value="/static/res/font-awesome/css/font-awesome.css"/>" rel="stylesheet">
        <link href="<c:url value="/static/res/main.css"/>" rel="stylesheet">
        <link href="<c:url value="/static/res/gradient-back.css"/>" rel="stylesheet">
        <link href="<c:url value="/static/res/statistics.css"/>" rel="stylesheet">
        <link href="<c:url value="/static/favicon.png"/>" rel="icon" type="image/png">
        <title>Advanced Bugtracker</title>
    </head>
    <body>
        <div class="main-viewport">
            <header class="header gradient-back">
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
                                Date Reported
                            </th>
                        </tr>
                        <c:forEach items="${reports}" var="report">
                            <tr bug-id="<c:out value="${report.bugReportId}" />" class="bug-report-record">
                                <td><c:out value="${report.name}" /></td>
                                <td><c:out value="${report.reporter}" /></td>
                                <td><c:out value="${report.dateReported}" /></td>
                                <td><i class="fa fa-times remove-bug-record"></i></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
            <div class="main-panel">
                <div class="reports-wrapper">
                    <form name="bug-report-details">
                        <span class="editable-control" control-type="input">
                            <h2 class="bug-report-title data">Bug details.</h2>
                            <button class="btn-lg edit-a-bug" style="display: none;">Edit Bug Report</button>
                            <input type="text" name="name" class="title-input" placeholder="Enter subject" style="display: none;">
                        </span>
                        <div class="mod-header">
                            <h3 class="details-section-header">Details</h3>
                            <div class="header-line"></div>
                        </div>
                        <div class="bug-details">
                            <input type="hidden" name="bug-report-id">
                            <p><span class="detail-label">Reported at: </span><span class="date-reported"></span></p>
                            <p><span class="detail-label">Resolution date: </span><span class="date-resolved"></span></p>
                            <p><span class="detail-label">Last updated at: </span><span class="date-updated"></span></p>
                            <p class="editable-control" control-type="input">
                                <span class="detail-label">Reported by: </span><span class="reporter data"></span>
                                <input type="text" name="reporter" style="display: none;">
                            </p>
                            <p class="editable-control" control-type="input">
                                <span class="detail-label">Desired resolution date: </span><span class="desired-resolution-date data"></span>
                                <input type="text" placeholder="yyyy-MM-dd" title="Date in format yyyy-MM-dd" name="desiredResolutionDate" style="display: none;">
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
                                    <option value="0">Reported</option>
                                    <option value="1">Confirmed</option>
                                    <option value="2">Open</option>
                                    <option value="3">Not a Bug</option>
                                    <option value="4">Fixed</option>
                                    <option value="5">Closed</option>
                                    <option value="6">Pending</option>
                                </select>
                            </p>
                            <p class="editable-control" control-type="select">
                                <span class="detail-label">Related to project: </span><span class="project data"></span>
                                <select name="project" style="display: none;">
                                    <option value="">Unspecified</option>
                                </select>
                            </p>
                        </div>
                        <div class="mod-header">
                            <h3 class="details-section-header">Labels</h3>
                            <div class="header-line"></div>
                        </div>
                        <div class="label-creator editable-control border-top" control-type="colorpicker" style="display: none;">
                            <input type="text" name="label-name" placeholder="Input label name">
                            <input type="color" name="label-color" value="#07b3eb" title="Select Label Color">
                            <i class="fa fa-plus add-label" aria-hidden="true" disabled></i>
                            <input type="hidden" name="labels-data" >
                        </div>
                        <div class="labels-area data"></div>
                        <div class="mod-header">
                            <h3 class="details-section-header">Description</h3>
                            <div class="header-line"></div>
                        </div>
                        <p class="editable-control border-top" control-type="textarea">
                            <span class="bug-description data"></span>
                            <textarea name="description" class="bug-description-edit dialog-bug-description" style="display: none;"></textarea>
                        </p>
                        <p class="editable-control" control-type="input">
                            <input type="submit" class="btn-md update-a-bug" value="Update Bug Report" style="display: none;">
                            <input type="button" class="btn-md cancel-update" value="Cancel" style="display: none;">
                        </p>
                    </form>
                </div>
            </div>
        </div>

        <div class="new-bug-report-dialog" style="display: none;">
            <h3 class="new-bug-report-dialog-title">Report a Bug.<i class="dialog-close-btn fa fa-times" aria-hidden="true"></i></h3>
            <div class="dialog-cont">
                <form name="new-bug-report">
                    <label for="name">Title:</label>
                    <input type="text" name="name" placeholder="Enter subject">
                    <label for="desiredResolutionDate">Desired Resolution Date: </label>
                    <input type="text" placeholder="yyyy-MM-dd" title="Date in format yyyy-MM-dd" name="desiredResolutionDate">
                    <label for="description">Describe your problem: </label>
                    <textarea name="description" class="dialog-bug-description"></textarea>
                    <label for="reporter">Your Name: </label>
                    <input type="text" name="reporter" placeholder="Enter your name">
                    <div class="label-creator">
                        <label class="labesl" for="label-name">Labels:</label>
                        <input type="text" name="label-name" placeholder="Input label name">
                        <input type="color" name="label-color" value="#07b3eb" title="Select Label Color">
                        <i class="fa fa-plus add-label" aria-hidden="true" disabled></i>
                        <div class="labels-area"></div>
                        <input type="hidden" name="labels-data" >
                    </div>
                    <label for="priority">Select Priority: </label>
                    <select name="priority">
                        <option value="0">Unspecified</option>
                        <option value="1">Minor</option>
                        <option value="2">Medium</option>
                        <option value="3">Major</option>
                        <option value="4">Critical</option>
                        <option value="5">Blocker</option>
                    </select>
                    <br>
                    <br>
                    <label for="project">Select Project: </label>
                    <select name="project">
                        <option value="">Unspecified</option>
                    </select>
                    <div class="submit-a-bug-cont">
                        <input type="submit" value="Submit" class="btn-md submit-a-bug">
                    </div>
                </form>
            </div>
        </div>

        <div class="info-toast toast-success gradient-back">
            <i class="fa fa-check-circle-o fa-2x" aria-hidden="true"></i>
            <div class="msg-area"></div>
        </div>
        <div class="info-toast toast-failure gradient-back">
            <i class="fa fa-times fa-2x" aria-hidden="true"></i>
            <div class="msg-area"></div>
        </div>

        <div class="bug-deletion-confirm new-bug-report-dialog" style="display: none;">
            <h3 class="new-bug-report-dialog-title">Delete Bug Record.<i class="dialog-close-btn fa fa-times" aria-hidden="true"></i></h3>
            <div class="dialog-message">
                Are you sure you want to delete this bug record?
            </div>
            <div class="dialog-buttons">
                <button class="delete-yes btn-md">Yes</button>
                <button class="delete-no btn-md">No</button>
            </div>
        </div>

        <script src="<c:url value="/static/app/utils/polyfill.js"/>"></script>
        <script src="<c:url value="/static/app/utils/utils.js"/>"></script>
        <script src="<c:url value="/static/app/utils/ajax.js"/>"></script>
        <script src="<c:url value="/static/app/main.js"/>"></script>
        <script src="<c:url value="/static/app/statistics.js"/>"></script>
    </body>
</html>
