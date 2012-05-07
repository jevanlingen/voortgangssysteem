package services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DbProcessor<T> {

	List<T> process(ResultSet rs) throws SQLException;
}
