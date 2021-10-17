function goToEnteredPageWithEnteredPress (event) {
	if (typeof event == 'undefined' && window.event) { 
		event = window.event; 
	}
	
    if (event.keyCode == 13) {
    	goToEnteredPage();
    	return false;
    }
}

function goToPage(pageNo) {
	var totalNoOfPages = $("input[name=totalNoOfPages]").val();
	var currentPage = $("input[name=pageNo]").val();
	
	if((pageNo*1) <= totalNoOfPages && (pageNo*1) > 0 && pageNo != currentPage) {
		$("input[name=pageNo]").val(pageNo);
		
		var paginationFormId = $("input[name=paginationFormId]").val();
		
		$("#"+paginationFormId).submit();
	} else {
		$("#txtGoto").val('');
	}
}

function prevPage() {
	var currentPage = $("input[name=pageNo]").val();
	
	if(currentPage > 1) {
		goToPage(--currentPage)
	}
}

function nextPage() {
	var currentPage = $("input[name=pageNo]").val();
	var maxNoOfPages = $("input[name=totalNoOfPages]").val();
	
	if(currentPage < maxNoOfPages) {
		goToPage(++currentPage)
	}
}

function goToEnteredPage() {
	var enteredPage = $("#txtGoto").val();
	
	if(enteredPage != '') {
		goToPage(enteredPage);
	}
	
	return false;
}

function changeNumberOfRecordsPerPage(noOfRecords) {
	if($("input[name=noOfRecordsPerPage]").val() != noOfRecords) {
		$("input[name=noOfRecordsPerPage]").val(noOfRecords);
		$("input[name=pageNo]").val(-1);
		goToPage(1);
	}
}