$(function() {

	// visible of #Company field
	$("#UserType").on('change', function(){ 
		var a = $( "#UserType option:selected" ).val();
		if (a==2) {
			$("#Company").show(); 
			}
		else {
			$("#Company").hide(); 
			}
	});
	
	
	
	
	
});