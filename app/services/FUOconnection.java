package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.Logger;

public class FUOconnection {
	
	public static Map<String, List<String>> executeSQLStatement(String sql, DbProcessor processor) {
		Connection conn = null;
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://acorn-mysql-5.finalist.nl/facturenonline";
		String user = "dashboard1";
		String password = "dE92Gl1F";

		ResultSet rs = null;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			Logger.info("Database connection FUO established");
			Statement statement = conn.createStatement();
			rs = statement.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (conn != null) {
				try {
					Map<String, List<String>> result = processor.process(rs);
					conn.close();
					Logger.info("Database connection FUO terminated");

					return result;
				} catch (Exception e) { /* ignore close errors */
				}
			}
		}

		throw new RuntimeException("Error in FUOConncection, Waarschijnlijk is de query fout.");
	}
}
