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

    var DATE_FORMAT_DISPLAY = 'dd.mm.yy';

    var $dataEventId = options.dataEventId;
    var $dataEventDiv = $('#' + options.dataEventDivId);
    var $dataEventTitle = $('#' + options.dataEventTitleId );


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

        var displayDate = $.datepicker.formatDate( DATE_FORMAT_DISPLAY, new Date(data.startDate) );
        $dataEventTitle.text(displayDate + " " + data.name);

        $("#chartContainer").dxChart({
//                dataSource: [
//                    //...
//                ],
//                series: {valueField: 'costs', argumentField: 'day'}
        });
    };

    //-----------------------------

    init();
};



