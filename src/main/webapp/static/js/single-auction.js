$(document).ready(function () {
    $("#bid").mask("###0.00");

    var endDate = $("#countdownDate").val();

    $("#countdown-placeholder").countdown(new Date(endDate),
        function (event) {
            $(this).text(
                event.strftime('%D dni %H:%M:%S')
            );
        });
});