(function () {
    var recordTableRows = selectAll('.bug-report-record'),
            updateBugForm = select('form[name=bug-report-details]'),
            newBugForm = select('form[name=new-bug-report]'),
            newBugBtn = select('.report-a-bug'),
            editBugBtn = select('.edit-a-bug');

    recordTableRows.forEach(function (el) {
        el.addEventListener('click', handleBugRecordClick);
    });

    newBugForm.addEventListener('submit', submitBug);
    updateBugForm.addEventListener('submit', updateBug);

    newBugBtn.addEventListener('click', showNewBugDialog);
    editBugBtn.addEventListener('click', enableBugEditing);

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
                descriptionEl = select('.bug-description'),
                bugReportUpdForm = select('form[name=bug-report-details]'),
                labelsInput = bugReportUpdForm.select('input[name=labels-data]'),
                labels;

        try {
            labels = JSON.parse(report.labels);
        } catch (e) {
            labels = [];
        }

        bugReportTitleEl.innerText = report.name;
        dateReportedEl.innerText = report.dateReported;
        dateResolvedEl.innerText = report.dateResolved;
        dateUpdatedEl.innerText = report.dateUpdated;
        reporterEl.innerText = report.reporter;
        priorityEl.innerText = resolvePriority(report.priority);
        stateEl.innerText = resolveStatus(report.state);
        projectEl.innerText = report.project;
        descriptionEl.innerText = report.description;

        labelsInput.value = report.labels;
        bugReportUpdForm.select('.labels-area').innerHTML = '';
        labels.forEach(function (el) {
            renderLabel(bugReportUpdForm, el);
        });
    }

    function enableBugEditing() {
        var editableContorls = updateBugForm.selectAll('.editable-control');

        editableContorls.forEach(function (el) {
            var ctrlType = el.getAttribute('control-type');

            if (ctrlType != 'colorpicker') {
                el.select(ctrlType).style.display = 'inline';
                if (el.select('.data')) {
                    el.select(ctrlType).value = el.select('.data').innerText;
                }
                el.select('.data').style.display = 'none';
            } else {
                el.style.display = 'inline';
            }
        });
    }

    function showNewBugDialog() {
        var dialog = select('.new-bug-report-dialog'),
                closeBtn = select('.dialog-close-btn'),
                addLabelBtn = dialog.select('input[name=add-label]'),
                labelName = dialog.select('input[name=label-name]');

        dialog.style.display = 'block';

        closeBtn.addEventListener('click', function () {
            dialog.style.display = 'none';
        });

        addLabelBtn.addEventListener('click', addLabel);
        labelName.addEventListener('input', function () {
            if (this.value.length > 0) {
                addLabelBtn.disabled = false;
            } else {
                addLabelBtn.disabled = true;
            }
        });

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

    function updateBug(ev) {
        ev.preventDefault();

        var ajax = new Ajax('put', document.location.href, {'Content-type': 'application/x-www-form-urlencoded'}),
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

    function addLabel() {
        var labelNameInput = this.parentNode.select('input[name=label-name]'),
                colorPickerInput = this.parentNode.select('input[name=label-color]'),
                addLabelBtn = this.parentNode.select('input[name=add-label]'),
                labelsDataInput = this.parentNode.select('input[name=labels-data]'),
                labelsData = [];

        try {
            labelsData = JSON.parse(labelsDataInput.value);
            for (var i = 0; i < labelsData.length; i++) {
                if (labelsData[i]['name'] == labelNameInput.value) {
                    return;
                }
            }
        } catch (e) {
            labelsData = [];
        }

        labelsData.push({
            name: labelNameInput.value,
            color: colorPickerInput.value
        });

        labelsDataInput.value = JSON.stringify(labelsData);

        renderLabel(this.parentNode);

        addLabelBtn.disabled = true;
        labelNameInput.value = '';
        labelNameInput.focus();
    }

    function renderLabel(form, label) {
        var labelDiv = document.createElement('div'),
                labelRemoveBtn = document.createElement('span'),
                labelsDataInput = form.select('input[name=labels-data]'),
                labelsArea = form.select('.labels-area');

        if (!label) {
            var colorPickerInput = form.select('input[name=label-color]'),
                    labelNameInput = form.select('input[name=label-name]');
        }

        labelDiv.className = 'bug-label';

        labelDiv.innerText = label ? label.name : labelNameInput.value;
        labelDiv.style.backgroundColor = label ? label.color : colorPickerInput.value;

        labelRemoveBtn.innerText = 'x';
        labelRemoveBtn.className = 'bug-label-remove-btn';

        labelRemoveBtn.addEventListener('click', function () {
            var deleteIndex = -1, labels = JSON.parse(labelsDataInput.value),
                    labelName = this.parentNode.innerText.substring(0, this.parentNode.innerText.length - 1);

            for (var i = 0; i < labels.length; i++) {
                if (labels[i]['name'] == labelName) {
                    deleteIndex = i;
                }
            }
            labels.splice(deleteIndex, 1);
            labelsDataInput.value = JSON.stringify(labels);
            this.parentNode.parentNode.removeChild(this.parentNode);
        });

        labelDiv.appendChild(labelRemoveBtn);
        labelsArea.appendChild(labelDiv);
    }
}());


