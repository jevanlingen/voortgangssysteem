package controllers;

import static play.libs.Json.toJson;
import models.persistence.DashboardProject;
import play.Logger;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import services.FUO;

public class Widget extends Controller {
	
	public static Result saveWidgets(Long dashboard_id) {		
		Form<models.view.ViewModelWidget> filledForm = form(models.view.ViewModelWidget.class).bindFromRequest();
		if (filledForm.hasErrors()) {
			Logger.error("FOUT IN WIDGET CONTROLLER - Waarschijnlijk lege UpdateTime"); //UPDATETIME mag niet leeg zijn, gaat nog fout op dit moment
			return redirect(routes.DashboardSettings.index(dashboard_id));
		}
		else {
			for(models.persistence.Widget widget : filledForm.get().getWidgetList()) {
				widget.update();
			}
		}
				
		return redirect(routes.DashboardSettings.index(dashboard_id));
	}
	
	public static Result getFuoVoortgangsrapportage(Long widget_id) {
		int fuo_id = DashboardProject.getProject(models.persistence.Widget.getWidget(widget_id).getProject_id()).getFuo_id();
		
		response().setContentType("application/json");
		return ok(toJson(FUO.getLastProgressReportById(fuo_id)));
	}
	
	public static Result getWidgets(Long project_id) {		
		response().setContentType("application/json");
		return ok(toJson(models.persistence.Widget.getWidgetsByProjectId(project_id)));
	}

}
