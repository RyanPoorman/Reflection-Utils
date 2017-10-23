package poorman.utils.sql.manager;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelationalMapper {

	public static <T> Object createMappedObject(Class<T> classType, ResultSet rs) throws SQLException {

		ResultSetMetaData rsmd = rs.getMetaData();
		List<String> columns = getColumns(rsmd);

		return null;
	}

	private static List<String> getColumns(ResultSetMetaData rsmd) throws SQLException {
		List<String> columns = new ArrayList<String>();

		for (int i = 0; i < rsmd.getColumnCount(); i++) {
			columns.add(rsmd.getColumnName(i));
		}

		return null;
	}

}
