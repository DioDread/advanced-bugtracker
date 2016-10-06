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

function resolvePriority(number) {
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

function resolveStatus(number) {
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
