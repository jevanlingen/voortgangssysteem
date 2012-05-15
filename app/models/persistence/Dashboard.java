package models.persistence;

import java.util.List;

import javax.persistence.*;

import play.data.Form;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
@SuppressWarnings("serial")
public class Dashboard extends Model {

	@Id
	@GeneratedValue
	private Long id;

	@Required
	@Column(unique = true)
	private String label;

	private String projectManager;

	public static final Finder<Long, Dashboard> FIND = new Finder<Long, Dashboard>(Long.class, Dashboard.class);

	public static List<Dashboard> all() {
		return FIND.all();
	}

	public static Dashboard getDashboard(Long id) {
		return FIND.byId(id);
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

	public static void validate(Form<Dashboard> form) {
		if (FIND.where().eq("label", form.data().get("label")).findList().size() > 0) {
			form.reject("label", "Naam bestaat al");
		}
	}
}
