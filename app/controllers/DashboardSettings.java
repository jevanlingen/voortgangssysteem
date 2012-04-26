package controllers;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avaje.ebeaninternal.server.persist.BindValues.Value;

import models.Dashboard;
import models.Project;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import API.FUO;
import API_models.Projectmanager;

public class DashboardSettings extends Controller {
	
	static Form<Dashboard> dashboardForm = form(Dashboard.class);
	
	public static Result index(Long id) throws SQLException {
		return ok(views.html.dashboardsettings.render( Dashboard.getDashboard(id), dashboardForm, FUO.getAllProjectManagers(), FUO.getProjectsProjectManagers()));
	}
	
	public static Result saveSetting(Long dashboard_id) throws SQLException {
		Form<Dashboard> filledForm = form(models.Dashboard.class).bindFromRequest();
			System.out.println(filledForm.data());
		if (filledForm.hasGlobalErrors()) {
			
			System.out.println("DashboardSettings - SaveSettings form contains errors");
			return badRequest(views.html.dashboardsettings.render(Dashboard.getDashboard(dashboard_id), filledForm, FUO.getAllProjectManagers(), FUO.getProjectsProjectManagers()));
		} else {			
			Dashboard dashboard = Dashboard.getDashboard(dashboard_id);		    
			
			//Use the filledForm.data() instead of the filleForm.get(), because the Dashboard object does not have a list of projects
			for (Map.Entry<String, String> entry: filledForm.data().entrySet()) {
				System.out.println(entry.getValue());
				if (entry.getKey().startsWith("projectContainer")) {
					String[] values = entry.getValue().split(",");
					Project project = new Project(Integer.parseInt(values[0]), Integer.parseInt(values[1]), values[2]);
					project.setDashboard_id(dashboard_id);
					Project.create(project);
				}
				if (entry.getKey().startsWith("projectManager")) {
					dashboard.setProjectManager(entry.getValue());
				}
			}
			
			dashboard.update();
			
			return redirect(routes.DashboardSettings.index(dashboard_id));
		}
	}
	
	public static Map<String, String> getProjectManagers(List<Projectmanager> projectmanagersList) {
		Map<String, String> projectmanagersMap = new HashMap<String, String>();
		for (Projectmanager projectmanager: projectmanagersList) {
			projectmanagersMap.put(projectmanager.getId()+"", projectmanager.getName());
		}		
		return projectmanagersMap;
	}

}
