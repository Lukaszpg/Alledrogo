$(document).ready(function() {
    $('select').material_select();

    initLoginModal();
    initCloseButtonClearAction();
    initSellDatePicker();
});

var initLoginModal = function() {
    $('#loginModal').modal({
        dismissible: true,
        opacity: .5,
        in_duration: 300,
        out_duration: 200,
        starting_top: '4%',
        ending_top: '10%',
        ready: function(modal, trigger) {
        },
        complete: function() { }
    });
};

var initCloseButtonClearAction = function() {
    $(".close-button-clear-action").click(function() {
        $("#search").val("");
    });
};

var initSellDatePicker = function() {
    $('#endDate').pickadate({
        selectMonths: true,
        selectYears: 15,
        format: 'yyyy-mm-dd'
    });

}