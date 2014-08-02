/**
 * Created by petrpopov on 09.05.14.
 */

var DataEventLoader = function(options) {

    "use strict";

    var params = {
        realPath: null,
        types: []
    };

    this.options = options;

    var GET_DATA_EVENT_URL = "/api/data/getDataEvent.data";
    var GET_DATA_EVENT_REPORT = "/api/data/getDataEventReport.action";

    var DATE_FORMAT_DISPLAY = 'dd.mm.yy';
    var TIME_FORMAT_DISPLAY = 'HH:mm:ss';

    var $dataEventId = options.dataEventId;
    var $dataEventDiv = $('#' + options.dataEventDivId);
    var $dataEventTitle = $('#' + options.dataEventTitleId );
    var $dataEventContentTbody = $('#' + options.dataEventContentTbodyId );
    var $dataEventDescription = $('#' + options.dataEventDescriptionId );
    var $startDate = $('#' + options.startDateId);
    var $endDate = $('#' + options.endDateId);
    var $timeDate = $('#' + options.timeDateId);
    var $chartPulseContainer = $('#' + options.chartContainerId);
    var $chartAccContainer = $('#' + options.chartAccContainerId);
    var $reportLink = $('#' + options.reportLinkId);


    //-----------------------------

    var getServerUrl = function(url) {
        return params.realPath + url;
    };

    var init = function() {
        $dataEventDiv.ready(function() {
            params.realPath = $('#realPath').text().trim();
            loadDataEvent($dataEventId);
        });
    };

    var loadDataEvent = function(id) {

        $.ajax({
            type: 'GET',
            url: getServerUrl(GET_DATA_EVENT_URL) + "?id="+id,
            success: function(data) {
                fillData(data);
            }
        });
    };

    var fillData = function(data) {
        if( !data ) {
            return;
        }

        fillDescriptionData(data);
        fillSensorGraph(data);
        fillSensorTable(data);
    };

    var fillDescriptionData = function(data) {

        var startDateObject = new Date(data.startDate);
        var endDateObject = new Date(data.endDate);

        var displayDate = $.datepicker.formatDate( DATE_FORMAT_DISPLAY, startDateObject );
        $dataEventTitle.text(displayDate + " " + data.name);

        $dataEventDescription.text(data.description);

        var startDate = $.datepicker.formatDate( DATE_FORMAT_DISPLAY, startDateObject );
        var startDateTime = $.datepicker.formatTime( TIME_FORMAT_DISPLAY,
            {hour: startDateObject.getHours(), minute: startDateObject.getMinutes(), second: startDateObject.getSeconds()} );
        $startDate.text(startDate + " " + startDateTime);

        var endDate = $.datepicker.formatDate( DATE_FORMAT_DISPLAY, endDateObject );
        var endDateTime = $.datepicker.formatTime( TIME_FORMAT_DISPLAY,
            {hour: endDateObject.getHours(), minute: endDateObject.getMinutes(), second: endDateObject.getSeconds()} );
        $endDate.text(endDate + " " + endDateTime);

        $timeDate.text(getTimeInReadableText(data));
    };

    var getTimeInReadableText = function(data) {
        var startDateObject = new Date(data.startDate);
        var endDateObject = new Date(data.endDate);

        var seconds = (endDateObject - startDateObject)/1000;
        var time = (new Date()).clearTime()
            .addSeconds(seconds)
            .toString('HH:mm:ss');

        return time;
    };

    var fillSensorGraph = function(data) {

        if( !data || !data.sensors ) {
            return;
        }

        var sensorsArray = $.map(data.sensors, function(val) {
            return {counter: val.counter, pulseMs: val.pulseMs, accX: val.accX, accY: val.accY, accZ: val.accZ};
        });

        fillSensorPulseGraph(sensorsArray);
        fillSensorAccGraph(sensorsArray);
    };

    var fillSensorPulseGraph = function(sensorsArray) {
        $chartPulseContainer.dxChart({
            dataSource: sensorsArray,

            commonSeriesSettings: {
                argumentField: "counter",
                type: "stackedLine"
            },

            series: [
                { valueField: "pulseMs", name: "Пульс", color: '#ffa500' },
//                { valueField: "accX", name: "X" },
//                { valueField: "accY", name: "Y" },
//                { valueField: "accZ", name: "Z" }
            ],

            argumentAxis:{
                grid:{
                    visible: true
                }
            },
            tooltip:{
                enabled: true
            },
            title: "Пульсограмма",
            legend: {
                verticalAlignment: "bottom",
                horizontalAlignment: "center"
            },
            ommonPaneSettings: {
                border:{
                    visible: true,
                    right: false
                }
            },

            commonAxisSettings: {
                label: {
                    overlappingBehavior: { mode: 'rotate', rotationAngle: 0 }
                }
            }
        });
    };

    var fillSensorAccGraph = function(sensorsArray) {
        $chartAccContainer.dxChart({
            dataSource: sensorsArray,

            commonSeriesSettings: {
                argumentField: "counter",
                type: "stackedLine"
            },

            series: [
//                { valueField: "pulseMs", name: "Пульс", color: '#ffa500' },
                { valueField: "accX", name: "X accelration" },
                { valueField: "accY", name: "Y accelration" },
                { valueField: "accZ", name: "Z accelration" }
            ],

            argumentAxis:{
                grid:{
                    visible: true
                }
            },
            tooltip:{
                enabled: true
            },
            title: "Ускорение по осям X, Y, Z",
            legend: {
                verticalAlignment: "bottom",
                horizontalAlignment: "center"
            },
            ommonPaneSettings: {
                border:{
                    visible: true,
                    right: false
                }
            },

            commonAxisSettings: {
                label: {
                    overlappingBehavior: { mode: 'rotate', rotationAngle: 0 }
                }
            }
        });
    };

    var fillSensorTable = function(data) {
        if( !data || !data.sensors ) {
            return;
        }

        $.each(data.sensors, function(n, sensor) {
            createSensorTableRow(sensor).appendTo($dataEventContentTbody);
        });
    };

    var createSensorTableRow = function(sensor) {
        var deviceDate = $.datepicker.formatDate( DATE_FORMAT_DISPLAY, new Date(sensor.deviceDate) );
        var systemDate = $.datepicker.formatDate( DATE_FORMAT_DISPLAY, new Date(sensor.systemDate) );

        var tr = $('<tr/>').append(
            $('<td/>').text(sensor.header)
        ).append(
            $('<td/>').text(sensor.counter)
        ).append(
            $('<td/>').text(systemDate)
        ).append(
            $('<td/>').text(deviceDate)
        ).append(
            $('<td/>').text(sensor.pulseMs)
        ).append(
            $('<td/>').text(sensor.accX)
        ).append(
            $('<td/>').text(sensor.accY)
        ).append(
            $('<td/>').text(sensor.accZ)
        );

        return tr;
    };

    var enableDownloadReportLink = function() {

        $reportLink.click(function() {
            $.ajax({
                type: 'GET',
                url: getServerUrl(GET_DATA_EVENT_REPORT),
                success: function(data) {
                    console.log(data);
                }
            });
        });
    };

    //-----------------------------

    init();
};



