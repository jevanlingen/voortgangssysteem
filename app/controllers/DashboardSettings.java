package controllers;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avaje.ebeaninternal.server.persist.BindValues.Value;

import models.Dashboard;
import models.DashboardProject;
import models.Widget;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import API.FUO;
import API_models.Projectmanager;

public class DashboardSettings extends Controller {
	
	static Form<Dashboard> dashboardForm = form(Dashboard.class);
	static Form<Widget> widgetForm = form(Widget.class);
	
	public static Result index(Long id) throws SQLException {
		Dashboard dashboard = Dashboard.getDashboard(id);		
		Form<Dashboard> dashboardForm = form(Dashboard.class).fill(dashboard);
		HashMap<Long, Form<Widget>> widgetHashMapById = getFormWidgetsOfDashboard(dashboard.id);
		
		return ok(views.html.dashboardsettings.render( dashboard, dashboardForm, widgetHashMapById, FUO.getAllProjectManagers(), FUO.getProjectsProjectManagers()));
	}
	
	public static Result saveSetting(Long dashboard_id) throws SQLException {
		Form<Dashboard> filledForm = form(models.Dashboard.class).bindFromRequest();
			System.out.println(filledForm.data());
		if (filledForm.hasGlobalErrors()) {			
			Logger.error("DashboardSettings - SaveSettings form contains errors");
			HashMap<Long, Form<Widget>> widgetHashMapById = getFormWidgetsOfDashboard(dashboard_id);			
			return badRequest(views.html.dashboardsettings.render(Dashboard.getDashboard(dashboard_id), filledForm, widgetHashMapById, FUO.getAllProjectManagers(), FUO.getProjectsProjectManagers()));
		} else {
		    DashboardProject.deleteProjectsByDashboard_id(dashboard_id); //Delete projects including widgets. Next step: delete only those project that are not in current request
			Dashboard dashboard = Dashboard.getDashboard(dashboard_id);
			
			//Use the filledForm.data() instead of the filleForm.get(), because the Dashboard object does not have a list of projects
			for (Map.Entry<String, String> entry: filledForm.data().entrySet()) {
				if (entry.getKey().startsWith("projectContainer")) {
					String[] values = entry.getValue().split(","); //[0] fuo_id, [1] owner_id, [2] name
					DashboardProject project = new DashboardProject(Integer.parseInt(values[0]), Integer.parseInt(values[1]), values[2]);
					project.setDashboard_id(dashboard_id);
					DashboardProject.create(project);
					
					createAndSaveWidgets(project.getId());
				}
				if (entry.getKey().startsWith("projectManager")) {
					dashboard.setProjectManager(entry.getValue());
				}
			}
			
			dashboard.update();
			
			return redirect(routes.DashboardSettings.index(dashboard_id));
		}
	}
	
	public static HashMap<Long, Form<Widget>> getFormWidgetsOfDashboard(Long dashboard_id) {
		HashMap<Long, Form<Widget>> widgetHashMapById = new HashMap<Long, Form<Widget>>();
		List<DashboardProject> dbprojects = DashboardProject.getProjectsByDashboardId(dashboard_id);
		for (DashboardProject dbproject : dbprojects) {
			List<Widget> widgets = Widget.getWidgetsByProjectId(dbproject.getId());
			for (Widget widget : widgets) {
				widgetHashMapById.put( widget.getId(), form(Widget.class).fill(widget));
			}			
		}
		
		return widgetHashMapById;
	}
	
	public static void createAndSaveWidgets(Long project_id) {
		//FUO Medewerkers
		Widget fuo_medewerkers = new Widget();
		fuo_medewerkers.setName("fuo_medewerkers");
		fuo_medewerkers.setProject_id(project_id);
		fuo_medewerkers.setActivated(true);
		fuo_medewerkers.setUpdateTime(120); //standard value, can be omitted
		Widget.create(fuo_medewerkers);		
		
		//Sonar
		Widget sonar = new Widget();
		sonar.setName("sonar");
		sonar.setProject_id(project_id);
		sonar.setActivated(true);
		Widget.create(sonar);		
	}
	
	public static Map<String, String> getProjectManagers(List<Projectmanager> projectmanagersList) {
		Map<String, String> projectmanagersMap = new HashMap<String, String>();
		for (Projectmanager projectmanager: projectmanagersList) {
			projectmanagersMap.put(projectmanager.getId()+"", projectmanager.getName());
		}		
		return projectmanagersMap;
	}

}
