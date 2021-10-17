jQuery(function($) { //on document.ready
	$('#fromDate').datepicker({
		dateFormat : "mm/dd/yy"
	});
	$('#toDate').datepicker({
		dateFormat : "mm/dd/yy"
	});
})

$(document).ready(
	function() {
		$("#lawyerId").change(
			function() {
				$("#inputMiles").val("");
				$("#inputZipCode").val("");
				$("#includedCities").val("");
				$("#excludedCities").val("");
				$("#customersLastNames").val("");
					
				if ($("#lawyerId").val() == '') {
					return;
				}
				
				$.ajax({
					url: "print/config",
					data: {"lawyerId": $("#lawyerId").val()}
				}).done(function(data) {
					console.log(data);

					if (data['distance'] > 0) {
						$("#inputMiles").val(data['distance']);
					}
					
					if (data['zipCodes'].length > 0) {
						$("#inputZipCode").val(data['zipCodes'].join());
					}
					
					
					if (data['includedCitiesNames'].length > 0) {
						$("#includedCities").val(data['includedCitiesNames'].join());
					}
					
					if (data['excludedCitiesNames'].length > 0) {
						$("#excludedCities").val(data['excludedCitiesNames'].join());
					}
					
					if (data['customersLastNames'].length > 0) {
						$("#customersLastNames").val(data['customersLastNames'].join());
					}		
				}).fail(function(){
					alert("Something went wrong, please try again latter!");
				});
			} 
		);	
})