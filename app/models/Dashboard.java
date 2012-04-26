package models;

import java.util.HashMap;
import java.util.List;

import javax.persistence.*;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Dashboard extends Model {

	@Id
	@GeneratedValue
	public Long id;

	@Required
	public String label;
	
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
		//TODO: Project do still stay alive in DB!
		getDashboard(id).delete();
	}
	
	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}
}
