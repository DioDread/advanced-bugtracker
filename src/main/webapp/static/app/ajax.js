/**
 * Ajax utility library representing constructor function for xmlhttprequest-based objects instantiation
 * have shortcust for most importantn XHR events (include: load, error, progress) also provide direct access 
 * to legacy onreadystatechange event handler.
 * 
 * Usage example:
 *  var ax = new Ajax('get', 'http://example.com');
 *  ax.success = function(data) {
 *      el.innerText = data.toString();
 *  }
 *  ax.call();
 *  
 * @param {string} method - HTTP request method - only GET, PUT, POST or DELETE allowed
 * @param {string} url - complete url for request.
 * @param {boolean} withCredentials - required for CORS, sign AJAX requests.
 * @returns {Ajax} - object for handling ajax, success, failure and progress callbacks need to be registred as listed
 * properties for correct work.
 */
var Ajax = function (method, url, withCredentials) {
    var xhr = new XMLHttpRequest(), STATUS_SUCCESS = 200, STATUS_FAILURE = 400,
            self = this;
    if (!xhr) {
        throw new Error('this browser doesn\'t support AJAX, please use modern browser.');
    }

    if (withCredentials) {
        xhr.withCredentials = withCredentials;
    } else {
        xhr.withCredentials = false;
    }

    xhr.addEventListener('load', function (ev) {
        if (!self.success) {
            throw new Error('no async handler for successful response handling are registered.');
        }
        if (!self.failure) {
            throw new Error('no async handler for failed response handling are registered.');
        }
        if (xhr.status >= STATUS_SUCCESS && xhr.status < STATUS_FAILURE) {
            self.success(xhr.response);
        } else {
            self.failure(xhr.response);
        }
    });

    xhr.addEventListener('error', function (ev) {
        if (self.failure) {
            self.failure(xhr.response);
        }
    });

    xhr.addEventListener('progress', function (ev) {
        if (self.progress) {
            self.progress(ev);
        }
    });

    if (this.stateHandler) {
        xhr.addEventListener('readystatechange', this.stateHandler);
    }
    /**
     * Performs call with parameters provided from constructor, use after callback registration;
     */
    this.call = function () {
        xhr.open(method, url);
        if (method.toUpperCase() == 'POST' || method.toUpperCase() == 'PUT') {
            xhr.send(this.data);
        } else {
            xhr.send();
        }
    };
};


