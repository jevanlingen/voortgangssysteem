package models.view;

import java.util.List;
import java.util.ArrayList;

import models.persistence.Dashboard;
import models.persistence.DashboardProject;

public class ViewModelDashboard {
	
	private Long id;
	
	private String label;
	
	private String projectManager;
	
	private List<DashboardProject> projects = new ArrayList<DashboardProject>();

	public static ViewModelDashboard getViewModelDashboard(Long id) {
		ViewModelDashboard vmd = new ViewModelDashboard();
		Dashboard d = Dashboard.getDashboard(id);
		
		vmd.id = d.getId();
		vmd.label = d.getLabel();
		vmd.projectManager = d.getProjectManager();
		vmd.setProjects(DashboardProject.getProjectsByDashboardId(id));
		
		return vmd;
	}

	public List<DashboardProject> getProjects() {
		return projects;
	}

	public void setProjects(List<DashboardProject> projects) {
		this.projects = projects;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}
}
