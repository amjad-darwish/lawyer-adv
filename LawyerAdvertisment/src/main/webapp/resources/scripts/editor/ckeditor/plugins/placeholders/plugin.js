CKEDITOR.plugins.add( 'placeholders',
{   
   requires : ['richcombo'], //, 'styles' ],
   init : function( editor )
   {
      var config = editor.config,
         lang = editor.lang.format;


      // Gets the list of tags from the settings.
      var tags = []; //new Array();
      //this.add('value', 'drop_text', 'drop_label');
      tags[0]=["${greeting}", "Greeting", "Greeting"];
      tags[1]=["${fName}", "First Name", "First Name"];
      tags[2]=["${lName}", "Last Name", "Last Name"];
      tags[3]=["${city}", "City", "City"];
      tags[4]=["${state}", "State", "State"];
      tags[5]=["${zipCode}", "Zip code", "Zip code"];
      tags[6]=["${streetAddress}", "Street address", "Street Address"];
      tags[7]=["${DOA}", "Date of accident", "Date of accident"];
      tags[8]=["${COA}", "City of accident", "City of accident"];
      tags[9]=["${GRD}", "Generated date", "The date of generating the report"];
      tags[10]=["${barcode}", "Bar code", "Bar code for the zip code"];
      tags[11]=["${ostate}", "Office state", "Office state"];
      tags[12]=["${ocity}", "Office city", "Office city"];
      tags[13]=["${oaddress}", "Office address", "Office street address"];
      tags[14]=["${ozip}", "Office zipcode", "Office zipcode"];
	  tags[15]=["${fault_code}", "At Fault Code", "At Fault Code"];
      
      // Create style objects for all defined styles.

      editor.ui.addRichCombo( 'PlaceHolders',
         {
            label : "Insert place holder",
            title :"Insert place holder",
            voiceLabel : "Insert place holder",
            className : 'cke_format',
            multiSelect : false,

            panel :
            {
               css : [ config.contentsCss, CKEDITOR.getUrl('skins/moono/editor.css' ) ],
               voiceLabel : lang.panelVoiceLabel
            },

            init : function()
            {
               this.startGroup( "PlaceHolders" );
               //this.add('value', 'drop_text', 'drop_label');
               for (i = 0; i < tags.length; i++){
                  this.add(tags[i][0], tags[i][1], tags[i][2]);
               }
            },

            onClick : function( value )
            {         
               editor.focus();
               editor.fire( 'saveSnapshot' );
               editor.insertHtml(value);
               editor.fire( 'saveSnapshot' );
            }
         });
   }
});