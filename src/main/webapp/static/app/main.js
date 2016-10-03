(function () {
    var recordTableRows = selectAll('.bug-report-record'),
            newBugForm = select('form[name=new-bug-report]'),
            newBugBtn = select('.report-a-bug');

    recordTableRows.forEach(function (el) {
        el.addEventListener('click', handleBugRecordClick);
    });

    newBugForm.addEventListener('submit', submitBug);

    newBugBtn.addEventListener('click', showNewBugDialog);

    fetchProjects();

    // Module functions

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

//        select('.new-bug-report-dialog > h3').addEventListener('mousedown', moveAt);
//        dialog.addEventListener('mouseup', function (e) {
//            document.onmouseup = null;
//            dialog.onmouseup = null;
//        });
//
//        function moveAt(e) {
//            dialog.style.left = e.pageX - dialog.offsetWidth / 2 + 'px';
//            dialog.style.top = e.pageY - dialog.offsetHeight / 2 + 'px';
//            document.onmousemove = function (e) {
//                moveAt(e);
//            };
//        }
    }

    function submitBug(ev) {
        ev.preventDefault();

        var ajax = new Ajax('post', document.location.href, {'Content-type': 'application/x-www-form-urlencoded'}),
                data = '';

        for (var i = 0; i < this.length; i++) {
            var el = this[i];
            if (el.type != 'submit' && el.type != 'button') {
                data += el.name + '=' + el.value + (i == this.lenght - 1 ? '' : '&');
            }
        }

        ajax.data = data;
        ajax.success = function (data) {
            var dbg = data;
        };
        ajax.failure = function (data) {
            var dbg = data;
        };

        ajax.call();
    }

    function fetchProjects() {
        var projectSelects = selectAll('select[name=project]'),
                projects = [],
                ajax = new Ajax('get', document.location.href + 'project', {'Accept': 'application/json'});

        ajax.success = function (data) {
            projects = data;
            projectSelects.forEach(function (el) {
                fillSelect(el);
            });
        };
        ajax.failure = function (data) {
        };

        function fillSelect(selectEl) {
            for (var i = 0; i < projects.length; i++) {
                var option = document.createElement('option');
                option.value = projects[i]['projectId'];
                option.innerText = projects[i]['name'];
                selectEl.appendChild(option);
            }
        }

        ajax.call();
    }
}());


