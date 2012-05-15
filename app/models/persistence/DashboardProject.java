package models.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
@SuppressWarnings("serial")
public class DashboardProject extends Model {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private int fuoId;
	
	private String clientName;
	
	@Required
	private String name;

	private int ownerId;
	
	private Long dashboardId;
	
	private String projecttypeDescription;
	
	public static Finder<Long, DashboardProject> find = new Finder<Long, DashboardProject>(Long.class, DashboardProject.class);

	public DashboardProject(int fuoId, int ownerId, String name) {
		this.fuoId = fuoId;
		this.ownerId = ownerId;
		this.name = name;
	}
	
	public DashboardProject(int fuo_id, String client_name, int owner_id, String name, String projecttype_description) {
		this.fuoId = fuo_id;
		this.clientName = client_name;
		this.ownerId = owner_id;
		this.name = name;
		this.projecttypeDescription = projecttype_description;
	}
	
	public static List<DashboardProject> getProjectsByDashboardId(Long id) {
		return find.where()
	            .eq("dashboardId", id)
	            .findList();
	}
	
	public static List<Long> getProjectIdsByDashboardId(Long id) {
		List<Long> ids = new ArrayList<Long>();
		List<Object> listObjectIds = find.where()
				.eq("dashboardId", id)
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
		 .eq("fuoId", dashboardProject.getFuoId())
         .eq("ownerId", dashboardProject.getOwnerId())
         .eq("dashboardId", dashboardProject.getDashboardId())
         .findRowCount();
		
		return (rowCount > 0) ? true : false;
	}
	
	public static boolean projectExist(int fuo_id, int owner_id, Long dashboard_id) {
		int rowCount = find.where()
		 .eq("fuoId", fuo_id)
         .eq("ownerId", owner_id)
         .eq("dashboardId", dashboard_id)
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

	public int getFuoId() {
		return fuoId;
	}

	public void setFuoId(int fuo_id) {
		this.fuoId = fuo_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int owner_id) {
		this.ownerId = owner_id;
	}

	public Long getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(Long dashboard_id) {
		this.dashboardId = dashboard_id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String client_name) {
		this.clientName = client_name;
	}

	public String getProjecttypeDescription() {
		return projecttypeDescription;
	}

	public void setProjecttypeDescription(String projecttype_description) {
		this.projecttypeDescription = projecttype_description;
	}
}