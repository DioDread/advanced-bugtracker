(function () {
    var recordTableRows = selectAll('.bug-report-record'),
            editBugForm = select('form[name=bug-report-details]'),
            editBugBtn = select('.edit-a-bug'),
            submitEditBugBtn = select('.update-a-bug'),
            editCancelBtn = select('.cancel-update'),
            newBugForm = select('form[name=new-bug-report]'),
            newBugBtn = select('.report-a-bug'),
            bugsCountEl = select('.header-message'),
            acceptJson = {'Accept': 'application/json'},
    isEditingInProgress = false, isCreationInProgress = false;

    recordTableRows.forEach(function (el) {
        el.addEventListener('click', handleBugRecordClick);
    });

    newBugForm.addEventListener('submit', submitBug);
    editBugForm.addEventListener('submit', function (ev) {
        ev.preventDefault();
    });

    newBugBtn.addEventListener('click', showNewBugDialog);
    if (editBugBtn) {
        editBugBtn.addEventListener('click', enableBugEditing);
    }
    submitEditBugBtn.addEventListener('click', updateBug);
    editCancelBtn.addEventListener('click', cancelBugEditing);

    fetchProjects();
    refreshBugCountMsg();

    select('.bugreports').selectAll('.remove-bug-record').forEach(function (btn) {
        btn.addEventListener('click', deleteBugRecord);
    });

    // Module functions

    function handleBugRecordClick(evt) {
        if (isEditingInProgress) {
            return;
        }
        var detailsHref = document.location.href + '/details?id=' + this.getAttribute('bug-id'),
                ajax = new Ajax('get', detailsHref, acceptJson);

        ajax.success = fillBugReportDetails;
        ajax.failure = defaultErrorHandler;

        ajax.call();
    }

    function deleteBugRecord(evt) {
        evt.bubbles = false;
        evt.stopPropagation();

        var bugId = this.parentNode.parentNode.getAttribute('bug-id'),
                deleteBugRequest = new Ajax('delete', document.location.href + '?bug-report-id=' + bugId),
                bugDeletionConfirm = select('.bug-deletion-confirm'),
                closeBtn = bugDeletionConfirm.select('.dialog-close-btn'),
                deleteYes = bugDeletionConfirm.select('.delete-yes'),
                deleteNo = bugDeletionConfirm.select('.delete-no');

        bugDeletionConfirm.style.display = 'block';

        deleteYes.addEventListener('click', function () {
            deleteBugRequest.success = function (data) {
                var debug = data;
                showToast(true, 'Bug record deleted sucessfully.');
                refreshPageState();
                closeDialog();
            };

            deleteBugRequest.failure = function (err) {
                showToast(false, 'Some error occured during bug record deletion process.');
                console.error(err);
            };

            deleteBugRequest.call();
        });

        deleteNo.addEventListener('click', closeDialog);
        closeBtn.addEventListener('click', closeDialog);

        function closeDialog() {
            bugDeletionConfirm.style.display = 'none';
        }
    }

    function refreshBugCountMsg() {
        if (isEditingInProgress) {
            return;
        }
        var detailsHref = document.location.href + '?response=json',
                getBugReportsList = new Ajax('get', detailsHref, acceptJson);

        getBugReportsList.success = function (data) {
            var filtered = data.filter(function (el) {
                return el.state != 4 && el.state != 5;
            });
            bugsCountEl.innerHTML = bugsCountEl.innerHTML.replace('{0}', filtered.length);
        };

        getBugReportsList.failure = defaultErrorHandler;
        getBugReportsList.call();
    }

    function refreshBugsList() {
        var table = select('.bugreports'),
                requestBugsList = new Ajax('get', document.location.href + '?response=json', acceptJson);

        requestBugsList.success = function (data) {
            var row, col;

            for (var i = table.rows.length - 1; i > 0; i--) {
                table.deleteRow(i);
            }

            for (var i = 0; i < data.length; i++) {
                var record = data[i];
                row = table.insertRow(i + 1);
                row.setAttribute('bug-id', record.bugReportId);

                col = document.createElement('td');
                col.innerText = record.name;
                row.appendChild(col);

                col = document.createElement('td');
                col.innerText = record.reporter;
                row.appendChild(col);

                col = document.createElement('td');
                col.innerText = record.dateReported;
                row.appendChild(col);

                col = document.createElement('td');
                btn = document.createElement('i');
                btn.className = 'fa fa-times remove-bug-record';
                btn.addEventListener('click', deleteBugRecord);
                col.appendChild(btn);

                row.addEventListener('click', handleBugRecordClick);
                row.appendChild(col);
            }
        };

        requestBugsList.failure = defaultErrorHandler;

        requestBugsList.call();
    }

    function fillBugReportDetails(report) {
        var bugReportTitleEl = select('.bug-report-title'),
                bugReportIdInput = editBugForm.select('input[name=bug-report-id]'),
                dateReportedEl = select('.date-reported'),
                editBugBtn = select('.edit-a-bug'),
                dateResolvedEl = select('.date-resolved'),
                dateUpdatedEl = select('.date-updated'),
                desiredResolutionEl = select('.desired-resolution-date'),
                reporterEl = select('.reporter'),
                assignedEl = select('.assigned'),
                priorityEl = select('.priority'),
                stateEl = select('.state'),
                projectEl = select('.project'),
                descriptionEl = select('.bug-description'),
                labelsInput = editBugForm.select('input[name=labels-data]'),
                labels;

        try {
            labels = JSON.parse(report.labels);
        } catch (e) {
            labels = [];
        }

        if (editBugBtn) {
            editBugBtn.style.display = 'inline';
        }

        bugReportIdInput.value = report.bugReportId;

        bugReportTitleEl.innerText = report.name;
        dateReportedEl.innerText = report.dateReported;
        dateResolvedEl.innerText = report.dateResolved == '' ? 'not yet resolved' : report.dateResolved;
        dateUpdatedEl.innerText = report.dateUpdated == '' ? 'never' : report.dateUpdated;
        reporterEl.innerText = report.reporter;
        assignedEl.innerText = report.reportedUser;
        desiredResolutionEl.innerText = report.desiredResolutionDate;
        priorityEl.innerText = resolvePriority(report.priority);
        stateEl.innerText = resolveState(report.state);
        projectEl.innerText = report.project;
        descriptionEl.innerText = report.description;

        labelsInput.value = report.labels;
        editBugForm.select('.labels-area').innerHTML = '';
        labels.forEach(function (el) {
            renderLabel(editBugForm, el);
        });
    }

    function enableBugEditing() {
        this.style.display = 'none';

        isEditingInProgress = true;

        var editableContorls = editBugForm.selectAll('.editable-control');

        editableContorls.forEach(function (el) {
            var ctrlType = el.getAttribute('control-type'),
                    data = el.select('.data');
            if (el.select(ctrlType)) {
                el.select(ctrlType).style.display = 'inline';
            }
            switch (ctrlType) {
                case 'colorpicker':
                    el.style.display = 'inline';
                    el.select('.add-label').addEventListener('click', addLabel);
                    editBugForm.select('.labels-area').selectAll('i').forEach(function (btn) {
                        btn.style.display = 'block';
                        btn.style.marginRight = 0;
                    });
                    break;
                case 'textarea':
                case 'input':
                    if (data) {
                        el.select(ctrlType).value = el.select('.data').innerText;
                        data.style.display = 'none';
                    }
                    if (el.selectAll('input').length > 1) {
                        el.selectAll('input').forEach(function (el) {
                            el.style.display = 'inline';
                        });
                    }
                    break;
                case 'select':
                    data.style.display = 'none';
                    setSelectOption(el.select('.data').innerText, el.select(ctrlType));
                    break;
                default:
            }
        });
    }

    function cancelBugEditing() {
        isEditingInProgress = false;

        editBugBtn.style.display = 'inline';

        var editableContorls = editBugForm.selectAll('.editable-control');

        editableContorls.forEach(function (el) {
            var ctrlType = el.getAttribute('control-type'),
                    ctrl = el.select(ctrlType),
                    data = el.select('.data');
            if (data) {
                if (data instanceof HTMLHeadingElement) {
                    data.style.display = 'inline';
                } else {
                    data.style.display = 'inline';
                }
            }
            switch (ctrlType) {
                case 'colorpicker':
                    el.style.display = 'none';
                    editBugForm.select('.labels-area').selectAll('i').forEach(function (btn) {
                        btn.style.display = 'none';
                        btn.style.marginRight = 0;
                    });
                    break;
                case 'textarea':
                case 'input':
                    if (data) {
                        data.innerText = ctrl.value;
                        ctrl.style.display = 'none';
                    }
                    if (el.selectAll('input').length > 1) {
                        el.selectAll('input').forEach(function (el) {
                            el.style.display = 'none';
                        });
                    }
                    break;
                case 'select':
                    data.style.display = 'inline';
                    ctrl.style.display = 'none';
                    data.innerText = ctrl[ctrl.selectedIndex].innerText;
                    break;
                default:
            }
        });
    }

    function showNewBugDialog() {
        isCreationInProgress = true;

        var dialog = select('.new-bug-report-dialog'),
                closeBtn = select('.dialog-close-btn'),
                addLabelBtn = dialog.select('.add-label'),
                labelName = dialog.select('input[name=label-name]');

        dialog.style.display = 'block';

        closeBtn.addEventListener('click', function () {
            dialog.style.display = 'none';
            isCreationInProgress = false;
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

        var ajax = new Ajax('post', document.location.href, {'Content-Type': 'application/x-www-form-urlencoded'}),
                data = '';

        for (var i = 0; i < this.length; i++) {
            var el = this[i];
            if (el.type != 'submit' && el.type != 'button') {
                data += el.name + '=' + el.value + (i == this.lenght - 1 ? '' : '&');
            }
        }

        ajax.data = data;
        ajax.success = function () {
            showToast(true, 'New bug report created successfully!');
            select('.new-bug-report-dialog').style.display = 'none';
            isCreationInProgress = false;
            refreshPageState();
        };
        ajax.failure = function (err) {
            console.log(err);
            showToast(false, 'New bug report creation failure!');
        };

        ajax.call();
    }

    function updateBug(ev) {
        var data = '';
        for (var i = 0; i < editBugForm.length; i++) {
            var el = editBugForm[i];
            if (el.name == 'label-name' || el.name == 'label-color')
                continue;
            if (el.type != 'submit' && el.type != 'button') {
                data += el.name + '=' + el.value + (i < editBugForm.length - 2 ? '&' : '');
            }
        }
        data = data.substr(0, data.length - 1);
        var ajax = new Ajax('post', document.location.href + '/details', {'Content-Type': 'application/x-www-form-urlencoded'});
        ajax.data = data;
        ajax.success = function () {
            showToast(true, 'But report updated, sucessfully!');
            cancelBugEditing();
            refreshPageState();
        };
        ajax.failure = function () {
            showToast(false, 'But report update error.');
        };

        ajax.call();
    }

    function fetchProjects() {
        var projectSelects = selectAll('select[name=project]'),
                projects = [],
                ajax = new Ajax('get', document.location.href + '/project', acceptJson);

        ajax.success = function (data) {
            projects = data;
            projectSelects.forEach(function (el) {
                fillSelect(el);
            });
        };
        ajax.failure = defaultErrorHandler;

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
                addLabelBtn = this.parentNode.select('.add-label'),
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
        if (isEditingInProgress) {
            renderLabel(this.parentNode.parentNode);
        } else {
            renderLabel(this.parentNode);
        }

        addLabelBtn.disabled = true;
        labelNameInput.value = '';
        labelNameInput.focus();
    }

    function renderLabel(form, label) {
        var labelDiv = document.createElement('div'),
                labelRemoveBtn = document.createElement('i'),
                labelsDataInput = form.select('input[name=labels-data]'),
                labelsArea = form.select('.labels-area');

        if (!label) {
            var colorPickerInput = form.select('input[name=label-color]'),
                    labelNameInput = form.select('input[name=label-name]');
        }

        labelDiv.className = 'bug-label';

        labelDiv.innerText = label ? label.name : labelNameInput.value;
        labelDiv.style.backgroundColor = label ? label.color : colorPickerInput.value;

        var rgb = hexToRgb(label ? label.color : colorPickerInput.value);
        var contrast = Math.round(((parseInt(rgb.r) * 299) + (parseInt(rgb.g) * 587) + (parseInt(rgb.b) * 114)) / 1000);

        if (contrast > 125) {
            labelDiv.style.color = 'black';
        } else {
            labelDiv.style.color = 'white';
        }

        labelRemoveBtn.className += 'fa fa-times-circle bug-label-remove-btn';

        if (!isEditingInProgress && !isCreationInProgress) {
            labelRemoveBtn.style.display = 'none';
        }

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

    function defaultErrorHandler() {
        showToast(false, 'Some error occured, please contact administrator.');
    }

    function refreshPageState() {
        refreshBugCountMsg();
        refreshBugsList();
    }
}());


