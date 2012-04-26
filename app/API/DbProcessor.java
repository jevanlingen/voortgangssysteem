package API;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface DbProcessor {

	Map<String, List<String>> process(ResultSet rs);	
}
