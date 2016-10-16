/**
 * Safe quyerySelector shortcut
 * @param {String} selector - html element valid css selector
 * @returns {HTMLElement} - first element found by specific css selector
 */
var select = function (selector) {
    if (selector && document.querySelector) {
        return document.querySelector.bind(document)(selector)
    }
    return null;
};

/**
 * Safe quyerySelectorAll shortcut
 * @param {String} selector - html element valid css selector
 * @returns {NodeList} - all elements by specific selector
 */
var selectAll = function (selector) {
    if (selector && document.querySelectorAll) {
        return document.querySelectorAll(selector);
    }
    return null;
};

if (!HTMLElement.prototype.select) {
    HTMLElement.prototype.select = HTMLElement.prototype.querySelector;
}

if (!HTMLElement.prototype.selectAll) {
    HTMLElement.prototype.selectAll = HTMLElement.prototype.querySelectorAll;
}

/**
 * Resolves State name from it's id;
 * @param {number} number - State Id
 * @returns {String} - State Name
 */
function resolveState(number) {
    switch (number) {
        case 0:
            return 'Reported';
        case 1:
            return 'Confirmed';
        case 2:
            return 'Open';
        case 3:
            return 'Not a Bug';
        case 4:
            return 'Fixed';
        case 5:
            return 'Closed';
        case 6:
            return 'Pending';
        default:
            return 'Open';
    }
}
/**
 * Resolve Priority name from it's id;
 * @param {number} number - Priority Id
 * @returns {String} - Priority Name
 */
function resolvePriority(number) {
    switch (number) {
        case 0:
            return 'Unspecified';
        case 1:
            return 'Minor';
        case 2:
            return 'Medium';
        case 3:
            return 'Major';
        case 4:
            return 'Critical';
        case 5:
            return 'Blocker';
        default:
            return 'Unspecified';
    }
}

/**
 * Set select ements selected option based on its text name
 * @param {String} text - element option to select name
 * @param {HTMLSelectElement} selectEl - select element
 */
function setSelectOption(text, selectEl) {

    switch (selectEl.name) {
        case 'state':
            setStateSelectOption(text, selectEl);
            break;
        case 'priority':
            setPrioritySelectOption(text, selectEl);
            break;
        case 'project':
            setProjectSelectOption(text, selectEl);
            break;
    }


    function setStateSelectOption(text, selectEl) {
        if (!selectEl && selectEl.tagName != 'select') {
            return;
        }
        switch (text) {
            case 'Reported':
                selectEl.selectedIndex = 0;
                break;
            case 'Confirmed':
                selectEl.selectedIndex = 1;
                break;
            case 'Open':
                selectEl.selectedIndex = 2;
                break;
            case 'Not a Bug':
                selectEl.selectedIndex = 3;
                break;
            case 'Fixed':
                selectEl.selectedIndex = 4;
                break;
            case 'Closed':
                selectEl.selectedIndex = 5;
                break;
            case 'Pengidng':
                selectEl.selectedIndex = 6;
                break;
            default:
                selectEl.selectedIndex = 0;
        }
    }

    function setPrioritySelectOption(text, selectEl) {
        if (!selectEl && selectEl.tagName != 'select') {
            return;
        }
        switch (text) {
            case 'Unspecified':
                selectEl.selectedIndex = 0;
                break;
            case 'Minor':
                selectEl.selectedIndex = 1;
                break;
            case 'Medium':
                selectEl.selectedIndex = 2;
                break;
            case 'Major':
                selectEl.selectedIndex = 3;
                break;
            case 'Critical':
                selectEl.selectedIndex = 4;
                break;
            case 'Blocker':
                selectEl.selectedIndex = 5;
                break;
            default:
                selectEl.selectedIndex = 0;
        }
    }

    function setProjectSelectOption(text, selectEl) {
        for (var i = 0; i < selectEl.length; i++) {
            if (selectEl[i].innerText == text) {
                selectEl.selectedIndex = i;
            }
        }
    }
}
/**
 * Show popup message with success or failure message in right corner of screen
 * @param {boolean} isSuccess - determine if toast will show success or failure message
 * @param {string} message - message text
 */
function showToast(isSuccess, message) {
    var toastElem = isSuccess ? select('.toast-success') : select('.toast-failure');
    
    if (isSuccess) {
        select('.toast-failure').classList.remove('appear-slow');
    } else {
        select('.toast-success').classList.remove('appear-slow');
    }

    toastElem.classList.add('appear-slow');
    toastElem.select('div').innerText = message;

    toastElem.addEventListener('click', function (ev) {

        if (toastElem.classList.contains('appear-slow')) {
            toastElem.classList.remove('appear-slow');
        }
    });
    
    setTimeout(function() {
        toastElem.classList.remove('appear-slow');
    }, 4000);
}

/**
 * Convert valid hex color string (includes #) to rgb representing object.
 * @param {String} hex color string for conversion
 * @returns {Object} rgb repsentation of given hex color string
 */
function hexToRgb(hex) {
    var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
    return result ? {
        r: parseInt(result[1], 16),
        g: parseInt(result[2], 16),
        b: parseInt(result[3], 16)
    } : null;
}