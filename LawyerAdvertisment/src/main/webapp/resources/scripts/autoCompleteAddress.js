const SmartyStreetsCore = SmartyStreetsSDK.core;

let webSiteKey = '30823566362733695';

let sharedClientBuilder = new SmartyStreetsCore.ClientBuilder(new SmartyStreetsCore.SharedCredentials(webSiteKey));
let autoCompleteClient = sharedClientBuilder.buildUsAutocompleteClient();

let staticClientBuilder = new SmartyStreetsCore.ClientBuilder(new SmartyStreetsCore.SharedCredentials(webSiteKey));
let usStreetClient = staticClientBuilder.buildUsStreetApiClient();
var showError = false;

$( "#txtAddress" ).autocomplete({
    source: function( request, response ) {
    	this.response = response;
    	
    	let lookup = new SmartyStreetsSDK.usAutocomplete.Lookup($("#txtAddress").val());
    	
    	autoCompleteClient.send(lookup)
    		.then(function(data) {
    			let options = [];
    			
    			data.result.forEach(function(e) {
    				options.push({label: e['text'], value: e['text'], details: {street: e['streetLine'], city: e['city'], state: e['state']}});
    			});
    			
    			response(options);
    		})
    		.catch(handleError);
    },
    minLength: 2,
    select: function( event, ui ) {
    	let lookup1 = new SmartyStreetsSDK.usStreet.Lookup();
    	
    	let item = ui['item'];
    	
    	lookup1.street = item['details']['street'];
    	lookup1.city = item['details']['city'];
    	lookup1.state = item['details']['state'];
    	
    	usStreetClient.send(lookup1)
    				  .then(handleSuccess)
    				  .catch(handleError);
    }
  } );

function handleSuccess(response) {
	if(response.lookups[0] == null || response.lookups[0].result[0] == null) {
		$("#txtAddress").val(response.lookups[0]['street']);
		$("input[name='cityName']").val(response.lookups[0]['city']);
		$("input[name='state']").val(response.lookups[0]['state']);
		$("input[name='apartment']").val("");
		$("input[name='fullZipCode']").val("");
		$("input[name='longitude']").val("");
		$("input[name='latitude']").val("");
		
		$("#verificationFlag").attr("hidden", false);
		$("#verificationFlag").attr("style","font-weight:bold; color: RED");
		$("#verificationFlag").text("Unverified");
				
		if (showError) {
			alert("The address is incorrect, please check it again!");
		}
		
		return;
	}
	
	var fullAddress = response.lookups[0].result[0];
	
	$("#txtAddress").val(fullAddress['deliveryLine1']);
	$("input[name='fullZipCode']").val(fullAddress['components']['zipCode'] + '-' + fullAddress['components']['plus4Code']);
	$("input[name='cityName']").val(fullAddress['components']['cityName']);
	$("input[name='state']").val(fullAddress['components']['state']);
	$("input[name='apartment']").val(fullAddress['components']['secondaryNumber']);
	$("input[name='longitude']").val(fullAddress["metadata"]["longitude"]);
	$("input[name='latitude']").val(fullAddress["metadata"]["latitude"]);

	if (fullAddress['analysis']['footnotes']) {
		$("#verificationFlag").attr("hidden", false);
		$("#verificationFlag").attr("style","font-weight:bold; color: RED");
		$("#verificationFlag").text("Unverified");
		
		if (showError) {
			alert("The address is incorrect, please check it again!");
		}
	} else {
		$("#verificationFlag").attr("hidden",false);
		$("#verificationFlag").attr("style","font-weight:bold; color: Green");
		$("#verificationFlag").text("verified");
	}
}

function handleError(response) {
	if (showError) {
		alert("(" + response.statusCode + ") " + response.error);
	}
	
	console.log(response);
}