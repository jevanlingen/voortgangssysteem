$(document).ready(function() {
	$('.'+$('#projectManager option:selected').val()).show();
	
	$('#projectManager').change(function() {	
		$('.hide').hide();
		$('.'+$('#projectManager option:selected').val()).show();
	});
	
	///---- WIDGET TABS ----///
	$("div[id*='widgetSetting_']").hide().first().show();
	$("div[id*='projectTab_']").first().addClass('active');
	
	$('.projectTab').click(function() {
		$('.projectTab').removeClass('active');
		$(this).addClass('active');
		
		$("div[id*='widgetSetting_']").hide();
		$('#widgetSetting_'+ $(this).attr('id').split('_')[1]).show();
	});
	///---- END WIDGET TABS ----///
	
	$('#settingsWidgetForm').submit(function() {
		$(".widgetSettingFrame").each(function(i) {
			$(this).find('input').each(function() {
				$(this).attr('name', $(this).attr('name').replace($(this).attr('name'), 'widgetList[' + i + '].'+$(this).attr('name')));
			});
		});
	});
	
});