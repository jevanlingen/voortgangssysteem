package models.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class DashboardProject extends Model {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private int fuo_id;
	
	private String client_name;
	
	@Required
	private String name;

	private int owner_id;
	
	private Long dashboard_id;
	
	public static Finder<Long, DashboardProject> find = new Finder<Long, DashboardProject>(Long.class, DashboardProject.class);

	public DashboardProject(int fuo_id, int owner_id, String name) {
		this.fuo_id = fuo_id;
		this.owner_id = owner_id;
		this.name = name;
	}
	
	public DashboardProject(int fuo_id, String client_name, int owner_id, String name) {
		this.fuo_id = fuo_id;
		this.client_name = client_name;
		this.owner_id = owner_id;
		this.name = name;
	}
	
	public static List<DashboardProject> getProjectsByDashboardId(Long id) {
		return find.where()
	            .eq("dashboard_id", id)
	            .findList();
	}
	
	public static List<Long> getProjectIdsByDashboardId(Long id) {
		List<Long> ids = new ArrayList<Long>();
		List<Object> listObjectIds = find.where()
				.eq("dashboard_id", id)
				.findIds();
		
		for (Object listObjectId : listObjectIds) {
			ids.add((Long) listObjectId);
		}
		
		return ids;		
	}
	
	public static DashboardProject getProject(Long id) {
		return find.byId(id);
	}
	
	public static boolean projectExist(DashboardProject dashboardProject) {
		int rowCount = find.where()
		 .eq("fuo_id", dashboardProject.getFuo_id())
         .eq("owner_id", dashboardProject.getOwner_id())
         .eq("dashboard_id", dashboardProject.getDashboard_id())
         .findRowCount();
		
		return (rowCount > 0) ? true : false;
	}
	
	public static boolean projectExist(int fuo_id, int owner_id, Long dashboard_id) {
		int rowCount = find.where()
		 .eq("fuo_id", fuo_id)
         .eq("owner_id", owner_id)
         .eq("dashboard_id", dashboard_id)
         .findRowCount();
		
		return (rowCount > 0) ? true : false;
	}
	
	public static void create(DashboardProject project) {
		project.save();
	}
	
	public static void delete(Long id) {
		Widget.deleteWidgetsByProject_id(id);
		find.byId(id).delete();
	}
	
	public static void deleteProjectsByDashboard_id(Long dashboard_id) {
		List<DashboardProject> dbprojects = getProjectsByDashboardId(dashboard_id);
		for (DashboardProject dbproject : dbprojects) {
			Widget.deleteWidgetsByProject_id(dbproject.getId());
			dbproject.delete();
		}
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

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
}