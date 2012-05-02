package models;

import java.util.ArrayList;
import java.util.List;


public class ViewModelWidget {
	
	List<Widget> widgetList = new ArrayList<Widget>();

	public List<Widget> getWidgetList() {
		return widgetList;
	}

	public void setWidgetList(List<Widget> widgetList) {
		this.widgetList = widgetList;
	}
}
