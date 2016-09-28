/**
 * Safe quyerySelector shortcut
 * @param {type} selector - html element valid css selector
 * @returns {Element} - first element found by specific css selector
 */
var select = function(selector) {
    if (selector && document.querySelector){
        return document.querySelector(selector);
    }
    return null;
};

/**
 * Safe quyerySelectorAll shortcut
 * @param {string} selector - html element valid css selector
 * @returns {NodeList} - all elements by specific selector
 */
var selectAll = function(selector) {
    if (selector && document.querySelectorAll){
        return document.querySelectorAll(selector);
    }
    return null;
};


