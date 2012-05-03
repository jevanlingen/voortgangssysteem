package controllers;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.api.Projectmanager;
import models.persistence.Dashboard;
import models.persistence.DashboardProject;
import models.persistence.Widget;
import models.view.ViewModelDashboard;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import services.FUO;

public class DashboardSettings extends Controller {
	
	public static Result index(Long id) throws SQLException {
		Dashboard dashboard = Dashboard.getDashboard(id);
		ViewModelDashboard viewModelDashboard = ViewModelDashboard.getViewModelDashboard(id);
				
		Form<ViewModelDashboard> dashboardForm = form(ViewModelDashboard.class).fill(viewModelDashboard);
		HashMap<Long, Form<Widget>> widgetHashMapById = getFormWidgetsOfDashboard(dashboard.getId());
		
		return ok(views.html.dashboardsettings.render( 
				dashboard, dashboardForm, widgetHashMapById, FUO.getAllProjectManagers(), FUO.getProjectsProjectManagers()));
	}
	
	public static Result saveSetting(Long dashboard_id) throws SQLException {
		Form<ViewModelDashboard> filledForm = form(ViewModelDashboard.class).bindFromRequest();
					
		if (filledForm.hasErrors()) {			
			Logger.error("DashboardSettings - SaveSettings form contains errors");
			HashMap<Long, Form<Widget>> widgetHashMapById = getFormWidgetsOfDashboard(dashboard_id);			
			return badRequest(views.html.dashboardsettings.render(
					Dashboard.getDashboard(dashboard_id),
					filledForm,
					widgetHashMapById,
					FUO.getAllProjectManagers(),
					FUO.getProjectsProjectManagers()));
		} else {			
			Dashboard dashboard = Dashboard.getDashboard(dashboard_id);
			dashboard.setProjectManager(filledForm.get().getProjectManager());
			
			List<Long> projectIdsOfPreviousRequest = DashboardProject.getProjectIdsByDashboardId(dashboard_id);
			List<DashboardProject> dashboardProjects = filledForm.get().getProjects();
			
			//Add or Update projects; chosen by user
			for (DashboardProject dashboardProject : dashboardProjects) {
				if(DashboardProject.projectExist(dashboardProject)) {
					dashboardProject.update();
				}
				else {
					DashboardProject.create(dashboardProject);
					createAndSaveWidgets(dashboardProject.getId());
				}
				projectIdsOfPreviousRequest.remove(dashboardProject.getId());
			}
			
			//Delete projects no longer chosen
			for(Long id : projectIdsOfPreviousRequest) {
				DashboardProject.delete(id);
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
	
	public static boolean projectExistInDB(DashboardProject dashboardProject, List<DashboardProject> dashboardProjectsInDB) {
		for (int i = 0; i < dashboardProjectsInDB.size(); i++) {
   			if ((dashboardProject.getName()).equals(dashboardProjectsInDB.get(i).getName())) {
   				return true;
   			}
   		}
		return false;
	}
	
	public static Long getProjectIdForExistedProject(DashboardProject dashboardProject, List<DashboardProject> dashboardProjectsInDB) {
		for (int i = 0; i < dashboardProjectsInDB.size(); i++) {
   			if ((dashboardProject.getName()).equals(dashboardProjectsInDB.get(i).getName())) {
   				return dashboardProjectsInDB.get(i).getId();
   			}
   		}
		
		return null;
	}

}
