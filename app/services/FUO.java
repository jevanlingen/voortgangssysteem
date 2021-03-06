package services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import models.api.Projectmanager;
import models.api.ProgressReport;
import models.persistence.DashboardProject;

public class FUO {

	public static List<Projectmanager> getAllProjectManagers() throws SQLException {
		String sql = "SELECT id, CASE WHEN (infix IS NULL) OR (infix = ' ') THEN concat(firstname, ' ', lastname) ELSE concat(firstname, ' ', infix, ' ', lastname) END AS name FROM view_voortgangsdashboard_employee WHERE id IN (SELECT DISTINCT owner_id FROM view_voortgangsdashboard_project WHERE DATE(end_date) >= DATE(NOW()) AND owner_id IS NOT NULL)";
		List<Projectmanager> projectManagers = FUOconnection.executeSQLStatement(
				sql, new DbProcessor<Projectmanager>() {
					@Override
					public List<Projectmanager> process(ResultSet rs) {
						List<Projectmanager> projectManagers = new ArrayList<Projectmanager>();
						
						try {
							while (rs.next()) {
								projectManagers.add( new Projectmanager(rs.getInt("id"), rs.getString("name")) );
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}									
						
						return projectManagers;
					}
		});	
		
		return projectManagers;
	}
	
	public static List<DashboardProject> getProjectsProjectManagers() throws SQLException {
		String sql = "SELECT DISTINCT view_voortgangsdashboard_project.id, view_voortgangsdashboard_client.name AS client_name, view_voortgangsdashboard_project.name, owner_id, projecttype_description FROM view_voortgangsdashboard_project, view_voortgangsdashboard_client WHERE DATE(end_date) >= DATE(NOW()) AND owner_id IS NOT NULL AND view_voortgangsdashboard_project.client_id = view_voortgangsdashboard_client.id";

		List<DashboardProject> projects = FUOconnection.executeSQLStatement(sql, new DbProcessor<DashboardProject>() {

			@Override
			public List<DashboardProject> process(ResultSet rs) {
				List<DashboardProject> projects = new ArrayList<DashboardProject>();
								
				try {
					while (rs.next()) {
						projects.add( new DashboardProject(rs.getInt("id"), rs.getString("client_name"), rs.getInt("owner_id"), rs.getString("name"), rs.getString("projecttype_description")) );
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
				
				return projects;
			}
		});
		
		return projects;
	}
	
	public static ProgressReport getLastProgressReportById(int id) {
		String sql = 
				"SELECT"
				  +"(SELECT SUM(hours) FROM view_voortgangsdashboard_registeredhours WHERE project_id = "+id+") AS hours_worked,"
				  +"SUM(hours_realised) AS hours_realised, SUM(hours_planned) AS hours_planned,"
				  +"SUM(hours_todo) AS hours_todo,"
				  +"(SELECT SUM(hours) FROM view_voortgangsdashboard_module WHERE project_id = "+id+") AS hours_total,"
				  +"(SELECT DATE(hours_collected_till) FROM view_voortgangsdashboard_progressreport WHERE project_id = "+id+") AS last_update_date "
				+"FROM view_voortgangsdashboard_progressline "
				+"WHERE progressreport_id ="
				+"("
					+"SELECT id FROM view_voortgangsdashboard_progressreport WHERE project_id = "+id
						+" AND "
					+"sequence = (SELECT MAX(sequence) FROM view_voortgangsdashboard_progressreport WHERE project_id = "+id+")"
				+");";
		
		List<ProgressReport> projects = FUOconnection.executeSQLStatement(sql, new DbProcessor<ProgressReport>() {

			@Override
			public List<ProgressReport> process(ResultSet rs) {
				List<ProgressReport> progressReportList = new ArrayList<ProgressReport>();
								
				try {
					while (rs.next()) {
						progressReportList.add( new ProgressReport(rs.getDouble("hours_worked"), rs.getDouble("hours_realised"), rs.getDouble("hours_planned"), rs.getDouble("hours_todo"), rs.getDouble("hours_total"), rs.getString("last_update_date")) );
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
				
				return progressReportList;
			}
		});
		
		return projects.get(0);
	}
	
	public static JSONArray getFuoModules(int project_id) {
		String sql = "SELECT name, view_voortgangsdashboard_module.hours AS planned_hours, SUM(view_voortgangsdashboard_registeredhours.hours) AS realised_hours FROM view_voortgangsdashboard_module, view_voortgangsdashboard_registeredhours WHERE view_voortgangsdashboard_module.project_id = "+project_id+" AND view_voortgangsdashboard_registeredhours.project_id = "+project_id+" AND view_voortgangsdashboard_module.id = view_voortgangsdashboard_registeredhours.module_id AND module_id IN (SELECT id FROM view_voortgangsdashboard_module WHERE project_id = "+project_id+") GROUP BY module_id";
		return FUOconnection.executeSQLStatement(sql);
	}
}

