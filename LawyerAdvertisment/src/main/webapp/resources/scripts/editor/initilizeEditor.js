$( document ).ready(function() {
	/*ClassicEditor.builtinPlugins.map( plugin => console.log(plugin.pluginName) );
	
	let editor;
		
	let mentionFeeds = ['${greeting}', '${fName}', '${lName}', '${city}', '${state}', '${zipCode}', '${streetAddress}',
	'${DOA}', '${COA}', '${GRD}', '${barcode}', '${off-state}', '${off-city}', '${off-address}', '${off-zip}'];
		
	ClassicEditor
		.create( document.querySelector( '#editor' ), {
				//licenseKey: 'license-key',
                toolbar: [ 'heading', '|', 
						'bold', 'italic', 'underline', 'strikethrough', '|', 
						'bulletedList', 'numberedList', '|', 
						'alignment', 'indent', 'outdent', '|',
						'insertTable', '|',
						'blockQuote', 'undo', 'redo', '|', 
						'fontColor', 'fontFamily', 'fontSize', 'fontBackgroundColor', '|', 
						/*'pageNavigation', 'previousPage', 'nextPage', 'pageBreak', '|',
						'specialCharacters', 'removeFormat', 'horizontalLine'],
                wordCount: {
                	container: document.getElementById( 'container-for-word-count' ),
                	displayWords: false
                },
                removePlugins: [ 'Title', 'Pagination'],
				table: {
            		 contentToolbar: ['tableColumn', 'tableRow', 'mergeTableCells', 
									'tableProperties', 'tableCellProperties'],
        		},
				mention: {
            		feeds: [
                		{
                    		marker: '$',
                    		feed: mentionFeeds,
                    		minimumCharacters: 0
                		}
            		]
        		},
				pagination: {
					pageWidth: '8.5in',
					pageHeight: '11in',
		
					pageMargins: {
						top: '20mm',
						bottom: '20mm',
						left: '12mm',
						right: '12mm'
					}
				},
            })
		.then( newEditor => {
	        	editor = newEditor;
				console.log(Array.from( editor.ui.componentFactory.names() ));
			})
        .catch( error => {
                console.error( error );
            });*/

CKEDITOR.replace( 'editor', {
	    customConfig: 'config.js'
} );
});