/**
 * Created by petrpopov on 09.05.14.
 */

var DataEventLoader = function(options) {

    "use strict";

    this.options = options;

    var GET_DATA_EVENT_URL = "/api/data/getDataEvent.data";

    var DATE_FORMAT_DISPLAY = 'dd.mm.yy';

    var $dataEventId = options.dataEventId;
    var $dataEventDiv = $('#' + options.dataEventDivId);
    var $dataEventTitle = $('#' + options.dataEventTitleId );


    //-----------------------------

    var init = function() {
        initDataEventDiv();
    };

    var initDataEventDiv = function() {

        $dataEventDiv.ready(function() {
            loadDataEvent($dataEventId);
        });

        var loadDataEvent = function(id) {

            $.ajax({
                type: 'GET',
                url: GET_DATA_EVENT_URL + "?id="+id,
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
        };
    };

    //-----------------------------

    init();
};



