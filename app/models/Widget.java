package models;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Widget extends Model {

	@Id
	@GeneratedValue
	private Long id;
	
	private Long project_id;

	@Required
	private String name;
	
	@Required
	private int updateTime = 120; //Standard update time 2 hours
	
	private boolean activated;
	
	@OneToMany(cascade=CascadeType.ALL)
	List<WidgetSetting> widgetSettings = new ArrayList<WidgetSetting>();
	
	public static Finder<Long, Widget> find = new Finder<Long, Widget>(Long.class, Widget.class);

	public static void create(Widget widget) {
		widget.save();
	}

	public static List<Widget> getWidgets(Long id) {
		return find.where()
	            .eq("id", id)
	            .findList();
	}
	
	public static List<Widget> getWidgetsByProjectId(Long project_id) {
		return find.where()
	            .eq("project_id", project_id)
	            .findList();
	}
	
	public static Widget getWidget(Long id) {
		return find.byId(id);
	}
	
	public static void delete(Long id) {
		find.byId(id).delete();
	}
	
	public static void deleteWidgetsByProject_id(Long project_id) {
		List<Widget> widgets = getWidgetsByProjectId(project_id);
		for (Widget widget : widgets) {
			widget.delete();
		}
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getProject_id() {
		return project_id;
	}

	public void setProject_id(Long project_id) {
		this.project_id = project_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(int updateTime) {
		this.updateTime = updateTime;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}
}
