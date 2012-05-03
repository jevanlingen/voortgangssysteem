package models.view;

import java.util.ArrayList;
import java.util.List;

import models.persistence.Widget;


public class ViewModelWidget {
	
	List<Widget> widgetList = new ArrayList<Widget>();

	public List<Widget> getWidgetList() {
		return widgetList;
	}

	public void setWidgetList(List<Widget> widgetList) {
		this.widgetList = widgetList;
	}
}
