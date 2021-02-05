var app = app || {};

app.init = function() {
	app.dateAndTimeHandler.setTimesOfFirstDay();
    app.dateAndTimeHandler.timeFilter();
};

app.dateAndTimeHandler = {
		
		setTimesOfFirstDay: function() {
		    if(typeof $("#date-selector").data('options') === "undefined") {
			    $("#date-selector").data('options',$('#time-selector option').clone());
		    } 
			var id = $("#date-selector").val();
			var options = $("#date-selector").data('options').filter('.' + id);
			console.log(options);
			$('#time-selector').html(options);
		},
		
		timeFilter: function(){
			$("#date-selector").change(function() {
			    if(typeof $(this).data('options') === "undefined") {
				    $(this).data('options',$('#time-selector option').clone());
			    } 
				var id = $(this).val();
				var options = $(this).data('options').filter('.' + id);
				console.log(options);
				$('#time-selector').html(options);
			});
		}
}

$(document).ready(app.init());