$(document).ready(function() {
	$('.'+$('#projectManager option:selected').val()).show();
	
	$('#projectManager').change(function() {	
		$('.hide').hide();
		$('.'+$('#projectManager option:selected').val()).show();
		$('.project input[type=checkbox]').prop("checked", false);
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
	$('.widgetSettingType select').each(function() {
		if($(this).val() == "bi") {
			$(this).parents('.widgetSettingFrame').find(".widgetSettingValue").hide();
		}
	});
	
	$('.widgetSettingType select').change(function() {	
		if($(this).val() == "bi") {
			$(this).parents('.widgetSettingFrame').find(".widgetSettingValue").hide();
		}
		else {
			$(this).parents('.widgetSettingFrame').find(".widgetSettingValue").show();
		}
	});
	
	///---- HIDE FIELDS FOR BI TYPES SETTINGWIDGETS ----///
	
	//Submit #settingsDashbaordForm
	$('#settingsDashbaordForm').submit(function() {
		$(".project:has(input[type=checkbox]:not(:checked))").each(function() {
			$(this).find('input[type=hidden]').remove();
		});
		$(".project:has(input[type=checkbox]:checked)").each(function(i) {
			$(this).find('input').each(function() {
				$(this).attr('name', $(this).attr('name').replace($(this).attr('name'), 'projects[' + i + '].'+$(this).attr('name')));
			});
		});
	});
	
	
	//Submit #SettingsWidgetForm
	$('#settingsWidgetForm').submit(function() {
		$(".widgetSettingFrame").each(function(i) {
			$(this).find('input, select').each(function() {
				$(this).attr('name', $(this).attr('name').replace($(this).attr('name'), 'widgetList[' + i + '].'+$(this).attr('name')));
			});
		});
	});
	
});