package controllers;

import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Widget extends Controller {
	
	public static Result saveWidgets(Long dashboard_id) {		
		Form<models.ViewModelWidget> filledForm = form(models.ViewModelWidget.class).bindFromRequest();
		if (filledForm.hasErrors()) {
			Logger.error("FOUT IN WIDGET CONTROLLER - Waarschijnlijk lege UpdateTime"); //UPDATETIME mag niet leeg zijn, gaat nog fout op dit moment
			return redirect(routes.DashboardSettings.index(dashboard_id));
		}
		else {
			for(models.Widget widget : filledForm.get().getWidgetList()) {
				widget.update();
			}
		}
				
		return redirect(routes.DashboardSettings.index(dashboard_id));
	}

}
