$( document ).ready(function() {
	$("#getGeoBtn").click(
		function() {
			if ($("#inputZipCode").val() == '') {
				alert("Zip code shouldn't be empty!");
			
				return;
			}
		
			$.ajax(
				{
					url: "getGeo",
					data: {"zipCode": $("#inputZipCode").val()}
				}
			).done(function(data) {
					console.log(data);
				
					$("#inputLatitude").val();
					$("#inputLongitude").val();

					if (data['longitude'] && data['latitude']) {
						$("#inputLatitude").val(data['latitude']);
						$("#inputLongitude").val(data['longitude']);
					} else {
						alert("Geo information isn't there, please enter them manually!")
					}					
				}).fail(function(){
					alert("Something went wrong, please enter the longitude and latitude manually!");
				});
		}
	);
});