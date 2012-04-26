package models;

import java.util.List;

import javax.persistence.*;


import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Project extends Model {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private int fuo_id;
	
	@Required
	private String name;

	private int owner_id;
	
	private Long dashboard_id;
	
	public static Finder<Long, Project> find = new Finder<Long, Project>(Long.class, Project.class);

	public Project(int fuo_id, int owner_id, String name) {
		this.fuo_id = fuo_id;
		this.owner_id = owner_id;
		this.name = name;
	}
	
	public static List<Project> getProjectsByDashboardId(Long id) {
		return find.where()
	            .eq("dashboard_id", id)
	            .findList();
	}
	
	public static Project getProject(Long id) {
		return find.byId(id);
	}
	
	public static void create(Project project) {
		project.save();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getFuo_id() {
		return fuo_id;
	}

	public void setFuo_id(int fuo_id) {
		this.fuo_id = fuo_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(int owner_id) {
		this.owner_id = owner_id;
	}

	public Long getDashboard_id() {
		return dashboard_id;
	}

	public void setDashboard_id(Long dashboard_id) {
		this.dashboard_id = dashboard_id;
	}
}