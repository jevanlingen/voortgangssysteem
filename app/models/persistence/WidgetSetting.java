package models.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class WidgetSetting extends Model {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private Long widget_id;
	
	private String name;
	private String value;
	
	public static Finder<Long, WidgetSetting> find = new Finder<Long, WidgetSetting>(Long.class, WidgetSetting.class);

	public static void create(WidgetSetting widgetSetting) {
		widgetSetting.save();
	}
	
	public static WidgetSetting getWidgetSetting(Long id) {
		return find.byId(id);
	}
	
	public static void delete(Long id) {
		find.byId(id).delete();
	}

	public Long getId() {
		return id;
	}

	public Long getWidget_id() {
		return widget_id;
	}

	public void setWidget_id(Long widget_id) {
		this.widget_id = widget_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
