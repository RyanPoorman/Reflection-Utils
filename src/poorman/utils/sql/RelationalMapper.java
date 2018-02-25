package poorman.utils.sql;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import poorman.utils.reflection.ReflectionUtils;

public class RelationalMapper {

	public static <T> T createMappedObject(Class<T> classType, ResultSetMetaData rsmd, ResultSet rs) {

		T obj = null;
		try {
			obj = classType.newInstance();

			Stack<Field> objFields = ReflectionUtils.getAllFieldsAsStack(classType);

			List<String> columns = getColumns(rsmd);

			populateNewObject(objFields, columns, obj, rs);

		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return obj;
	}

	private static List<String> getColumns(ResultSetMetaData rsmd) throws SQLException {
		List<String> columns = new ArrayList<String>();

		for (int i = 0; i < rsmd.getColumnCount(); i++) {
			columns.add(rsmd.getColumnLabel(i));
		}
		return null;
	}

	public static <T> void populateNewObject(Stack<Field> objFields, List<String> columns, T obj, ResultSet rs)
			throws IllegalArgumentException, IllegalAccessException, SQLException {

		for (int j = 0; j < objFields.size(); j++) {
			for (int i = 0; i < columns.size(); i++) {
				if (objFields.elementAt(j).getName().equalsIgnoreCase(columns.get(i))) {
					updateField(objFields.elementAt(j), obj, columns, i, rs);
					objFields.removeElementAt(i);
					continue;
				}
			}
		}

	}

	private static <T> void updateField(Field field, T obj, List<String> columns, int i, ResultSet rs)
			throws IllegalArgumentException, IllegalAccessException, SQLException {

		field.setAccessible(true);
		field.set(obj, rs.getObject(columns.get(i)));

	}

}
