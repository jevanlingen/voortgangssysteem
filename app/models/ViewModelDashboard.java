package models;

import java.util.List;
import java.util.ArrayList;

public class ViewModelDashboard {
	
	public Long id;
	
	public String label;
	
	public String projectManager;
	
	List<DashboardProject> projects = new ArrayList<DashboardProject>();

	public static ViewModelDashboard getViewModelDashboard(Long id) {
		ViewModelDashboard vmd = new ViewModelDashboard();
		Dashboard d = Dashboard.getDashboard(id);
		
		vmd.id = d.id;
		vmd.label = d.label;
		vmd.projectManager = d.getProjectManager();
		vmd.projects = DashboardProject.getProjectsByDashboardId(id);
		
		return vmd;
	}

}
