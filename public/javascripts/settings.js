$(document).ready(function() {
	$('.'+$('#projectManager option:selected').val()).show();
	
	jQuery('#projectManager').change(function() {	
		$('.hide').hide();
		$('.'+$('#projectManager option:selected').val()).show();
	});
});