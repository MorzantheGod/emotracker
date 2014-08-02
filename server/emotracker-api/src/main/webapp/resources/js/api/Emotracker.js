/**
 * Created by petrpopov on 07.05.14.
 */

var Emotracker = function(options) {

    "use strict";

    this.options = options;

    var params = {
        realPath: null,
        types: []
    };

    var GET_LAST_EVENTS_URL = "/api/data/getDataEvents.data";
    var DATA_EVENT_PAGE_URL = "/dataevent";

    var DATE_FORMAT_DISPLAY = 'dd.mm.yy';

    var $dataEventTbody = $('#' + options.dataEventTbodyId );
    var $dataEventTable = $('#' + options.dataEventTableId );
    var $dataEventAlert = $('#' + options.dataEventAlertId );


    //-----------------------------

    var getServerUrl = function(url) {
        return params.realPath + url;
    };

    var init = function() {

        $dataEventTbody.ready(function() {
            params.realPath = $('#realPath').text().trim();

            loadDataTable();
        });
    };

    var loadDataTable = function() {
        $.ajax({
            type: 'GET',
            url: getServerUrl(GET_LAST_EVENTS_URL),
            success: function(data) {
                fillDataTable(data);
            }
        });
    };

    var fillDataTable = function(data) {
        if( !data || data.length === 0 ) {
            $dataEventAlert.show();
            $dataEventTable.hide();
            return;
        }

        $dataEventAlert.hide();

        $.each(data, function(n, val) {
            createDataEventTableRow(n, val).appendTo($dataEventTbody);
        });
    };

    var createDataEventTableRow = function(n, val) {
        var displayDate = $.datepicker.formatDate( DATE_FORMAT_DISPLAY, new Date(val.startDate) );

        var url = getServerUrl(DATA_EVENT_PAGE_URL) + "/" + val.id;

        var tr = $('<tr/>').append(
            $('<td/>').append(
                $('<a/>').attr("href", url).text(n+1)
            )
        ).append(
            $('<td/>').append(
                $('<a/>').attr("href", url).text(displayDate)
            )
        ).append(
            $('<td/>').text(getTimeInReadableText(val))
        ).append(
            $('<td/>').append(
                $('<a/>').attr("href", url).text(val.name ? val.name : "")
            )
        ).append(
            $('<td/>').text(val.description ? val.description : "")
        );

        return tr;
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

    //------------------------------

    init();
};

