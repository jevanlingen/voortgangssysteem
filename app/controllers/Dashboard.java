package controllers;

import java.util.List;

import models.api.ProgressReport;
import models.persistence.DashboardProject;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import services.FUO;
import static play.libs.Json.toJson;

public class Dashboard  extends Controller {
	
	public static Result getProjects(Long dashboard_id) {
		models.persistence.Dashboard dashboard = models.persistence.Dashboard.getDashboard(dashboard_id);
		List<DashboardProject> listDashboardProjects = DashboardProject.getProjectsByDashboardId(dashboard_id);
		
		return ok(views.html.dashboard.render(dashboard, listDashboardProjects));
	}
}
