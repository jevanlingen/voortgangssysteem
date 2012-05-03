package models.persistence;

import java.util.List;

import javax.persistence.*;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Dashboard extends Model {

	@Id
	@GeneratedValue
	private Long id;

	@Required
	private String label;
	
	@Column(name = "project_manager")
	private String projectManager;
		
	public static Finder<Long, Dashboard> find = new Finder<Long, Dashboard>(Long.class, Dashboard.class);

	public static List<Dashboard> all() {
		return find.all();
	}
	
	public static Dashboard getDashboard(Long id) {
		return find.byId(id);
	}

	public static void create(Dashboard dashboard) {
		dashboard.save();
	}

	public static void delete(Long id) {
		DashboardProject.deleteProjectsByDashboard_id(id);
		getDashboard(id).delete();
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
