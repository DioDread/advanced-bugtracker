(function () {
    var recordTableRows = selectAll('.bug-report-record'),
        newBugBtn = select('.report-a-bug');
    
    recordTableRows.forEach(function (el) {
        el.addEventListener('click', handleBugRecordClick);
    });
    
    newBugBtn.addEventListener('click', showNewBugDialog);
    
    function handleBugRecordClick(evt) {
        var detailsHref = document.location.href + 'details?id=' + this.getAttribute('bug-id'),
                ajax = new Ajax('get', detailsHref, {'Accept': 'application/json'});
        
        ajax.success = fillBugReportDetails;
        
        ajax.failure = function (data) {
            var dbg = data;
        };
        
        ajax.call();
    }
    
    function fillBugReportDetails(report) {
        var bugReportTitleEl = select('.bug-report-title'),
            dateReportedEl = select('.date-reported'),
            dateResolvedEl = select('.date-resolved'),
            dateUpdatedEl = select('.date-updated'),
            reporterEl = select('.reporter'),
            priorityEl = select('.priority'),
            stateEl = select('.state'),
            projectEl = select('.project'),
            descriptionEl = select('.bug-description');

        bugReportTitleEl.innerText = report.name;
        dateReportedEl.innerText = report.dateReported;
        dateResolvedEl.innerText = report.dateResolved;
        dateUpdatedEl.innerText = report.dateUpdated;
        reporterEl.innerText = report.reporter;
        priorityEl.innerText = report.priority;
        stateEl.innerText = report.state;
        projectEl.innerText = report.project;
        
        descriptionEl.innerText = report.description;
    }
    
    function showNewBugDialog() {
        var dialog = select('.new-bug-report-dialog'),
            closeBtn = select('.dialog-close-btn');
        
        dialog.style.display = 'block';
        
        closeBtn.addEventListener('click', function () {
            dialog.style.display = 'none';
        });
    }
}());


