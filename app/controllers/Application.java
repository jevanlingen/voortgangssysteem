package controllers;

import play.data.Form;
import play.mvc.*;

import models.*;

public class Application extends Controller {

	static Form<Dashboard> dashboardForm = form(Dashboard.class);

	public static Result index() {
		return redirect(routes.Application.getDashboards());
	}

	public static Result getDashboards() {
		return ok(views.html.index.render(models.Dashboard.all(), dashboardForm));
	}
	
	public static Result createDashboard() {
		Form<Dashboard> filledForm = dashboardForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(views.html.index.render(Dashboard.all(), filledForm));
		} else {
			Dashboard.create(filledForm.get());
			return redirect(routes.Application.getDashboards());
		}
	}
	
	public static Result deleteDashboard(Long id) {
		Dashboard.delete(id);
		return redirect(routes.Application.getDashboards());
	}

//	public static Result javascriptRoutes() {
////		response().setContentType("text/javascript");
////		return ok(Routes.javascriptRouter("jsRoutes",
////				controllers.routes.javascript.Project.getWidgetInformation()
////		));
//		
//		// TODO
//		return null;
//	}

}