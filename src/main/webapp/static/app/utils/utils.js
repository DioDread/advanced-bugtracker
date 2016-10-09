/**
 * Safe quyerySelector shortcut
 * @param {type} selector - html element valid css selector
 * @returns {Element} - first element found by specific css selector
 */
var select = function (selector) {
    if (selector && document.querySelector) {
        return document.querySelector.bind(document)(selector)
    }
    return null;
};

/**
 * Safe quyerySelectorAll shortcut
 * @param {string} selector - html element valid css selector
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

function setSelectOption(text, selectEl) {

    switch (selectEl.name) {
        case 'state':
            setStateSelectOption(text, selectEl);
            break;
        case 'priority':
            setPrioritySelectOption(text, selectEl);
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
}

function setStateSelectOption(number, selectEl) {
    if (selectEl && selectEl.tagName == 'select') {
        selectEl.selectedIndex = number;
    }
}