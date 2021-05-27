var app = app || {};

app.init = function() {
	app.orderHandler.setTimesOfFirstDay();
	app.orderHandler.timeFilter();
	app.orderHandler.setFirstPaymentRadioToChecked();
	app.orderHandler.listeningFormChecks();
};

app.orderHandler = {
		
		setTimesOfFirstDay: function() {
			if(typeof $("#date-selector").data('options') === "undefined") {
				$("#date-selector").data('options',$('#time-selector option').clone());
			} 
			var id = $("#date-selector").val();
			var options = $("#date-selector").data('options').filter('.' + id);
			$('#time-selector').html(options);
			$('#time-selector').prop('selectedIndex', 0);
		},
		
		timeFilter: function(){
			$("#date-selector").change(function() {
				if(typeof $(this).data('options') === "undefined") {
				    $(this).data('options',$('#time-selector option').clone());
				} 
				var id = $(this).val();
				var options = $(this).data('options').filter('.' + id);
				$('#time-selector').html(options);
				$('#time-selector').prop('selectedIndex', 0);
			});
		},
		
		setFirstPaymentRadioToChecked: function () {
			var radioSection = document.getElementById("payment-options-row");
			var paymentOptionBoxes = radioSection.getElementsByClassName("form-check");
			var firstPaymentOptionBox = paymentOptionBoxes[0];
			firstPaymentOptionBox.classList.add("active");
			var firstPaymentOptionBoxRadioInput = firstPaymentOptionBox.getElementsByClassName("form-check-input")[0];
			firstPaymentOptionBoxRadioInput.checked = true;
		},

		listeningFormChecks: function(){
			var paymentOptionsRow = document.getElementById('payment-options-row');
			var formChecks = paymentOptionsRow.getElementsByClassName('form-check');
			for (var index = 0; index < formChecks.length; index++) {
				formChecks[index].addEventListener('click', function (event) {
					var previousActiveFormCheck = document.getElementsByClassName("form-check active")[0];
					var previousCheckedRadioInput = previousActiveFormCheck.getElementsByClassName("form-check-input")[0];
					previousActiveFormCheck.classList.remove("active");
					previousCheckedRadioInput.checked = false;
					this.classList.add("active");
					var currentRadioInput = this.getElementsByClassName("form-check-input")[0];
					currentRadioInput.checked = true;
			    })
			}
		}
	   
}

$(document).ready(app.init());