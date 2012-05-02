package API;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.DashboardProject;

import API_models.Projectmanager;

public class FUO {

	public static List<Projectmanager> getAllProjectManagers() throws SQLException {
		String sql = "SELECT id, CASE WHEN (infix IS NULL) OR (infix = ' ') THEN concat(firstname, ' ', lastname) ELSE concat(firstname, ' ', infix, ' ', lastname) END AS name FROM view_voortgangsdashboard_employee WHERE id IN (SELECT DISTINCT owner_id FROM view_voortgangsdashboard_project WHERE DATE(end_date) >= DATE(NOW()) AND owner_id IS NOT NULL)";
		Map<String, List<String>> result = FUOconnection.executeSQLStatement(
				sql, new DbProcessor() {

					@Override
					public Map<String, List<String>> process(ResultSet rs) {
						final Map<String, List<String>> result = new HashMap<String, List<String>>();
						final List<String> ids = new ArrayList<String>();
						final List<String> members = new ArrayList<String>();

						try {
							while (rs.next()) {
								ids.add(rs.getString("id"));
								members.add(rs.getString("name"));
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						result.put("id", ids);
						result.put("name", members);

						return result;

					}
				});
		
		List<Projectmanager> projectManagers = new ArrayList<Projectmanager>();
		for (int i = 0; i < result.get("id").size(); i++) {
			projectManagers.add(new Projectmanager( Integer.parseInt(result.get("id").get(i)), result.get("name").get(i)) );
		}		
		
		return projectManagers;
	}
	
	public static List<DashboardProject> getProjectsProjectManagers() throws SQLException {
		String sql = "SELECT DISTINCT id, name, owner_id FROM view_voortgangsdashboard_project WHERE DATE(end_date) >= DATE(NOW()) AND owner_id IS NOT NULL";
		Map<String, List<String>> result = FUOconnection.executeSQLStatement(
				sql, new DbProcessor() {

					@Override
					public Map<String, List<String>> process(ResultSet rs) {
						Map<String, List<String>> result = new HashMap<String, List<String>>();
						List<String> ids = new ArrayList<String>();
						List<String> name = new ArrayList<String>();
						List<String> owner_id = new ArrayList<String>();

						try {
							while (rs.next()) {
								ids.add(rs.getString("id"));
								name.add(rs.getString("name"));
								owner_id.add(rs.getString("owner_id"));
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						result.put("id", ids);
						result.put("name", name);
						result.put("owner_id", owner_id);

						return result;

					}
				});
		
		List<DashboardProject> projects = new ArrayList<DashboardProject>();
		for (int i = 0; i < result.get("id").size(); i++) {
			projects.add( new DashboardProject( Integer.parseInt(result.get("id").get(i)), Integer.parseInt(result.get("owner_id").get(i)), result.get("name").get(i)) );
		}	
		
		return projects;
	}
}

