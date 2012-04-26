package models;

import javax.persistence.*;

import play.data.validation.Constraints.Required;
import play.mvc.Result;

@Entity
public abstract class Widget {

	@Id
	@GeneratedValue
	private Long id;

	@Required
	private String label;
	
	@Required
	private int updateTime;
	
	private boolean activated = true;
	
	public abstract Result getInformation();

	public Long getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
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
