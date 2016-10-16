(function () {
    var statisticsBtn = select('.show-stats'),
            viewPort = document.createElement('canvas'),
            context = viewPort.getContext("2d"),
            container = document.createElement('div'),
            currentHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0),
            currentWidth = Math.max(document.documentElement.clientWidth, window.innerWidth || 0),
            bugsByDays = [];


    container.className = 'statistics-cont gradient-back';

    var w = viewPort.width = Math.round(currentWidth * .85);
    var h = viewPort.height = Math.round(currentHeight * .65);

    context.fillStyle = '#FFF';
    context.fillRect(0, 0, viewPort.width, viewPort.height);

    statisticsBtn.addEventListener('click', showStatistics);

    function showStatistics() {
        var statisticsRequest = new Ajax('get', document.location.href + 'statistics', {'Accept': 'application/json'});

        statisticsRequest.success = drawStatisticChart;
        statisticsRequest.failure = function (err) {
            var dbg = err;
        };
        statisticsRequest.call();
        container.appendChild(viewPort);
        document.body.appendChild(container);
    }

    function drawStatisticChart(data) {
        drawAxis();
        data.forEach(function(bug) {
            countBugs(Object.keys(bug));
        });
        var debug = data;
    }

    function drawAxis() {
        context.fillStyle = "#000";
        context.beginPath();
        context.moveTo(40, h - 40);
        context.lineTo(40, 40);
        context.lineTo(34, 46);
        context.moveTo(46, 46);
        context.lineTo(40, 40);
        context.moveTo(w - 40, h - 40);
        context.lineTo(w - 46, h - 46);
        context.moveTo(w - 46, h - 36);
        context.lineTo(w - 40, h - 40);
        context.lineTo(40, h - 40);
        context.stroke();
        context.stroke();
        context.stroke();
    }

    function countBugs(key) {
        if (bugsByDays[key] != undefined) {
            bugsByDays[key] = bugsByDays[key] + 1;
        } else {
            bugsByDays[key] = 1;
        }
    }
}());
