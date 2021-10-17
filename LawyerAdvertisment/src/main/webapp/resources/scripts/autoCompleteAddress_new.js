/*
pro look up

const SmartyStreetsCore = SmartyStreetsSDK.core;
const Lookup = SmartyStreetsSDK.usAutocompletePro.Lookup;

let websiteKey = "30823568273113906";
const credentials = new SmartyStreetsCore.SharedCredentials(websiteKey);

let client = SmartyStreetsCore.buildClient.usAutocompletePro(credentials);*/

const SmartyStreetsCore = SmartyStreetsSDK.core;
const Lookup = SmartyStreetsSDK.usAutocomplete.Lookup;

// for client-side requests (browser/mobile), use this code:
let key = '30823568273113906';
const  credentials = new SmartyStreetsCore.SharedCredentials(key);

let client = SmartyStreetsCore.buildClient.usAutocomplete(credentials);

$( "#txtAddress" ).autocomplete({
    source: function( request, response ) {
    	let lookup = new Lookup(request['term']);

    	client.send(lookup)
    		.then(function (suggestions) {
				let options = [];
			    			
				suggestions.result.forEach(
					function(suggestion) {
						options.push(buildAddress(suggestion));
			    	});
			    			
				response(options);
			})
			.catch(handleError);
    },
    minLength: 2,
    select: function( event, ui ) {
    	let item = ui['item'];
		let lookup1 = new Lookup(item['value']);
		
		/*    	
    	lookup1.selected = item["value"];
			
		if (item['expanded']) {
			client.send(lookup1)
				.then($(this).autocomplete())
				.catch(handleError);
		} else {
			client.send(lookup1)
				.then(handleSuccess)
				.catch(handleError);
		}*/
		
    	lookup1.street = item['details']['street'];
    	lookup1.city = item['details']['city'];
    	lookup1.state = item['details']['state'];
    	
    	client.send(lookup1)
			  .then(handleSuccess)
			  .catch(handleError);
		
    }
  } );

function buildAddress(suggestion) {
	let expanded = false;
	
	/*
	let whiteSpace = "";
	
	if (suggestion.secondary) {
		if (suggestion.entries > 1) {
			suggestion.secondary += " (" + suggestion.entries + " entries)";
		}
		
		whiteSpace = " ";
		expanded = true;
	}*/
	
	// let address = suggestion.street_line + whiteSpace + suggestion.secondary + " " + suggestion.city + ", " + suggestion.state + " " + suggestion.zipcode;
	
	let address = suggestion['text'];
	
	return {label: address, value: address, details: {street: suggestion['street_line'], city: suggestion['city'], state: suggestion['state'], zipcode: suggestion['zipcode'], expanded: expanded}};	
}

function handleSuccess(response) {
	console.log(response);
	
	if(response.lookups[0] == null || response.lookups[0].result[0] == null) {
		$("#txtAddress").val(response.lookups[0]['street']);
		$("input[name='cityName']").val(response.lookups[0]['city']);
		$("input[name='state']").val(response.lookups[0]['state']);
		$("input[name='apartment']").val("");
		$("input[name='zipCode']").val("");
		$("input[name='longitude']").val("");
		$("input[name='latitude']").val("");
		
		$("#verificationFlag").attr("hidden", false);
		$("#verificationFlag").attr("style","font-weight:bold; color: RED");
		$("#verificationFlag").text("Unverified");
				
		alert("The address is incorrect, please check it again!");
		return;
	}
	
	var fullAddress = response.lookups[0].result[0];
	
	$("#txtAddress").val(fullAddress['deliveryLine1']);
	$("input[name='zipCode']").val(fullAddress['components']['zipCode']);
	$("input[name='cityName']").val(fullAddress['components']['cityName']);
	$("input[name='state']").val(fullAddress['components']['state']);
	$("input[name='apartment']").val(fullAddress['components']['secondaryNumber']);
	$("input[name='longitude']").val(fullAddress["metadata"]["longitude"]);
	$("input[name='latitude']").val(fullAddress["metadata"]["latitude"]);

	if (fullAddress['analysis']['footnotes']) {
		$("#verificationFlag").attr("hidden", false);
		$("#verificationFlag").attr("style","font-weight:bold; color: RED");
		$("#verificationFlag").text("Unverified");
		
		alert("The address is incorrect, please check it again!");
	} else {
		$("#verificationFlag").attr("hidden",false);
		$("#verificationFlag").attr("style","font-weight:bold; color: Green");
		$("#verificationFlag").text("verified");
	}
}

function handleError(response) {
	alert("(" + response.statusCode + ") " + response.error);
	console.log(response);
}