package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import play.Logger;

public class FUOconnection {	
	static Connection conn = null;
		
	public static <T> List<T> executeSQLStatement(String sql, DbProcessor<T> processor) {
		ResultSet rs = null;

		try {
			Class.forName(FUOconfiguration.getDriver());
			conn = DriverManager.getConnection(FUOconfiguration.getUrl(), FUOconfiguration.getUser(), FUOconfiguration.getPassword());
			Logger.info("Database connection FUO established");
			Statement statement = conn.createStatement();
			rs = statement.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (conn != null) {
				try {
					List<T> result = processor.process(rs);
					//No closing connection, as it consumes to much time to open en close the connection for every call
					//Releases this Connection object's database and JDBC resources immediately instead of waiting for them to be automatically released.
					//conn.close();
					Logger.info("Database connection FUO terminated");

					return result;
				} catch (Exception e) { /* ignore close errors */
				}
			}
		}

		throw new RuntimeException("Error in FUOConncection, Waarschijnlijk is de query fout.");
	}
	
	public static JSONArray executeSQLStatement(String sql) {
		ResultSet rs = null;

		try {
			Class.forName(FUOconfiguration.getDriver());
			conn = DriverManager.getConnection(FUOconfiguration.getUrl(), FUOconfiguration.getUser(), FUOconfiguration.getPassword());
			Logger.info("Database connection FUO established");
			Statement statement = conn.createStatement();
			rs = statement.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (conn != null) {
				try {
					JSONArray result = convertResultSetToJSON(rs);
					//conn.close();
					Logger.info("Database connection FUO terminated");

					return result;
				} catch (Exception e) { /* ignore close errors */
				}
			}
		}
		
		throw new RuntimeException("Error in FUOConncection, Waarschijnlijk is de query fout.");
	}
	
	
	public static JSONArray convertResultSetToJSON(java.sql.ResultSet rs){

        JSONArray json = new JSONArray();

        try {

             java.sql.ResultSetMetaData rsmd = rs.getMetaData();

             while(rs.next()){
                 int numColumns = rsmd.getColumnCount();
                 JSONObject obj = new JSONObject();

                 for (int i=1; i<numColumns+1; i++) {

                     String column_name = rsmd.getColumnName(i);

                     if(rsmd.getColumnType(i)==java.sql.Types.ARRAY){
                         obj.put(column_name, rs.getArray(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.BIGINT){
                         obj.put(column_name, rs.getInt(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.BOOLEAN){
                         obj.put(column_name, rs.getBoolean(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.BLOB){
                         obj.put(column_name, rs.getBlob(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.DOUBLE){
                         obj.put(column_name, rs.getDouble(column_name)); 
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.FLOAT){
                         obj.put(column_name, rs.getFloat(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.INTEGER){
                         obj.put(column_name, rs.getInt(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.NVARCHAR){
                         obj.put(column_name, rs.getNString(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.VARCHAR){
                         obj.put(column_name, rs.getString(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.TINYINT){
                         obj.put(column_name, rs.getInt(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.SMALLINT){
                         obj.put(column_name, rs.getInt(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.DATE){
                         obj.put(column_name, rs.getDate(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.TIMESTAMP){
                        obj.put(column_name, rs.getTimestamp(column_name));   
                     }
                     else{
                    	 if(rs.getObject(column_name) == null) {
                    		 obj.put(column_name, JSONObject.NULL); 
                    	 }
                    	 else {
                    		 obj.put(column_name, rs.getObject(column_name));
                    	 }
                     } 

                    }//end foreach
                 
                 json.put(obj);

             }//end while




        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
                
        return json;
    }
}
