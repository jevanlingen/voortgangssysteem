package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import play.db.ebean.Model;

@Entity
public class Settings extends Model {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "project_manager")
	private String projectManager;

	@ManyToMany(cascade = CascadeType.REMOVE)
	public Map<Integer, Project> projects = new HashMap<Integer, Project>();

	private List<Integer> projectids = new ArrayList<Integer>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public List<Integer> getProjectids() {
		return projectids;
	}

	public void setProjectids(List<Integer> projectids) {
		this.projectids = projectids;
	}
	
	public Map<Integer, Project> getProjects() {
		return projects;
	}
	
	public void setProjects(Map<Integer, Project> map) {
		this.projects = map;
	}
	
//	public void addProjectById(Integer value, String label) {
//		Project project = new Project(value, 27, label, (long) 22);
//		project.save();
//		this.getProjects().put(value, project);
//		this.saveManyToManyAssociations("projects");
//	}
//	
//	public void addProjectsByProjectIdList(List<Integer> list) {
//		for(Integer value: list) {
//			this.addProjectById(value, "TEST");
//		}
//	}
	
//	public void addAndUpdateProjectList(Long id) {
//		//Delete all the projects, so a new load from FUO will be 'a fresh load'
//		this.getProjects().clear();
//		
//		//Query naar FUO, zodat gegevens kunnen opgehaald worden. SELECT * FROM detinated tables WHERE projectid != in (ignoredProjectids)
//		//use id of project fuo as id for project voortgangsproject. 
//		
//		//for each project
//			Project p = new Project(23273, "testProject");
//			
//			//Add the widgets
//				Sonar sonar = new Sonar();
//				sonar.setLabel("SonarTest");
//				sonar.setUpdateTime(3);
//				
//				p.addWidget("SonarTest", sonar);
//				
//				//next widget
//				//..etc
//			//end add widgets to project
//			
//			//add project to HashMap
//			this.getProjects().put(23273, p); //use same id as id of projectobject; it's easier to find that way
//	    //end each
//	}
}
