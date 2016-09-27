(function () {
    var recordTableRows = document.querySelectorAll('.bug-report-record');
    
    recordTableRows.forEach(function (el) {
        el.addEventListener('click', handleBugRecordClick);
    });
    
    function handleBugRecordClick(evt) {
        var detailsHref = document.location.href + 'details?id=' + this.getAttribute('bug-id'),
                ajax = new Ajax('get', detailsHref);
        
        ajax.success = function (data) {
            var dbg = data;
        };
        
        ajax.failure = function (data) {
            var dbg = data;
        };
        
        ajax.call();
        //TODO: AJAX call to /details? and display bug info to bug details
    }
}());


