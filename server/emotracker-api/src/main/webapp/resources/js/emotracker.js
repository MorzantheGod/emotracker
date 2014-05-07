/**
 * Created by petrpopov on 07.05.14.
 */

$( document ).ready(function() {

    var GET_LAST_EVENTS = "/api/data/getDataEvents.data";

    var $dataEventTable = $('#dataEventTable');

    //------------------------

    var init = function() {

        $dataEventTable.ready(function() {

            $.ajax({
                type: 'GET',
                url: GET_LAST_EVENTS,
                success: function(data) {
                    if( !data ) {
                        return;
                    }

                    $.each(data, function(n, val) {

                        var tr = $('<tr/>').append(
                            $('<td/>').text(n)
                        ).append(
                            $('<td/>').text(val.startDate)
                        ).append(
                            $('<td/>').text(val.name)
                        ).append(
                            $('<td/>').text(val.description)
                        ).appendTo($dataEventTable);

                    });
                }
            });
        });
    };


    //--------------------------
    init();
});