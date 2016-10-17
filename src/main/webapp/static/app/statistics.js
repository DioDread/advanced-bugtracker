(function () {
    var statisticsBtn = select('.show-stats'),
            viewPort = document.createElement('canvas'),
            context = viewPort.getContext('2d'),
            container = document.createElement('div'),
            header = document.createElement('h3'),
            closeBtn = document.createElement('i'),
            currentHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0),
            currentWidth = Math.max(document.documentElement.clientWidth, window.innerWidth || 0),
            bugsByDays = [];

    container.className = 'statistics-cont gradient-back';
    container.style.display = 'none';
    header.className = 'new-bug-report-dialog-title';
    header.innerText = 'Bug reporting activity chart.';
    closeBtn.className = 'dialog-close-btn fa fa-times';

    header.appendChild(closeBtn);
    container.appendChild(header);

    closeBtn.addEventListener('click', function () {
        container.style.display = 'none';
    });

    var w = viewPort.width = Math.round(currentWidth * .85);
    var h = viewPort.height = Math.round(currentHeight * .65);

    statisticsBtn.addEventListener('click', showStatistics);

    container.appendChild(viewPort);
    document.body.appendChild(container);

    function showStatistics() {
        var statisticsRequest = new Ajax('get', document.location.href + 'statistics', {'Accept': 'application/json'});

        statisticsRequest.success = showStatisticsDialog;
        statisticsRequest.failure = function (err) {
            console.error(err);
        };
        statisticsRequest.call();
    }

    function showStatisticsDialog(data) {
        context.fillStyle = '#FFF';
        context.fillRect(0, 0, viewPort.width, viewPort.height);
        drawAxis();
        container.style.display = 'block';
        initChartData(data);
        drawChart();
    }

    function drawAxis() {
        context.lineWidth = 2;
        context.beginPath();
        context.strokeStyle = '#000000';
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
        context.closePath();

        context.font = '18px arial';
        context.fillStyle = '#2e57ea';
        context.fillText('Date Reported', w - 130, h - 50);
        context.save();
        context.rotate(-(Math.PI / 180) * 90);
        context.fillText('Bugs Count', -150, 30);
        context.restore();
    }

    function initChartData(data) {
        bugsByDays = [];
        data.forEach(function (bug) {
            countBugs(Object.keys(bug));
        });
    }

    function drawChart() {
        var xAxisGrade = [], yAxisGrade = [];

        fillAxisScales(xAxisGrade, yAxisGrade);

        context.lineWidth = 8;
        context.lineJoin = context.lineCap = 'round';
        context.beginPath();
        context.strokeStyle = '#2e57ea';
        context.moveTo(40, h - 40);
        context.font = '12px arial';
        context.fillStyle = '#000';


        for (var date in bugsByDays) {
            if (bugsByDays.hasOwnProperty(date)) {
                var y = yAxisGrade[bugsByDays[date]];
                var x = xAxisGrade[date];
                context.lineTo(x, y);
                context.fillText(date, x, h - 20);
                context.fillText(bugsByDays[date], 20, y);
            }
        }
        context.stroke();
        context.closePath();
    }

    function fillAxisScales(xAxisGrade, yAxisGrade) {
        var xSectionLength = Math.round((w - 80) / datesCount()),
                ySectionLength = Math.round((h - 80) / maxBugsCount()),
                dates = Object.keys(bugsByDays).map(function (date) {
            return (new Date(date)).getTime();
        }),
                minDate = Math.min.apply(null, dates) - 86400000;

        yAxisGrade[0] = h - 40;
        for (var i = 1; i < maxBugsCount(); i++) {
            yAxisGrade[i] = yAxisGrade[i - 1] - ySectionLength;
        }

        var nextDay = minDate,
                nextDate = dateToFormat(nextDay), step = 40;
        for (var i = 1; i <= datesCount(); i++) {
            xAxisGrade[nextDate] = step;
            nextDay += 86400000;
            step += xSectionLength;
            nextDate = dateToFormat(nextDay);
        }
    }

    function dateToFormat(mils) {
        var date = new Date(mils),
                month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
        return date.getFullYear() + '-' + month + '-' + date.getDate();
    }

    function maxBugsCount() {
        var max = 0;
        for (var date in bugsByDays) {
            if (!bugsByDays.hasOwnProperty(date))
                continue;
            if (bugsByDays[date] > max)
                max = bugsByDays[date];
        }
        return max;
    }

    function datesCount() {
        var oneDay = 86400000,
                dates = Object.keys(bugsByDays).map(function (date) {
            return (new Date(date)).getTime();
        });
        var maxDate = Math.max.apply(null, dates) + oneDay,
                minDate = Math.min.apply(null, dates) - oneDay,
                delta = maxDate - minDate;
        return Math.ceil(delta / oneDay);
    }

    function countBugs(key) {
        if (bugsByDays[key] != undefined) {
            bugsByDays[key] = bugsByDays[key] + 1;
        } else {
            bugsByDays[key] = 1;
        }
    }
}());
