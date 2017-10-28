package poorman.utils.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import poorman.utils.objects.ClassesUtils;

public class SqlStatementUtils {

	private ResultSet rs;
	private int returnSize;

	public ResultSet executeSelect(String sql) {
		return executeSelect(sql, new Object[0]);
	}

	public ResultSet executeSelect(String sql, Object... params) {

		try (Connection conn = getConnection(); PreparedStatement ps = createPreparedStatement(conn, sql, params)) {
			this.rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return this.rs;
	}

	public static Object executeQuery(String sql, Class<?> classType) {
		return executeQuery(sql, classType, Integer.MAX_VALUE, new Object[0]);
	}

	public static Object executeQuery(String sql, Class<?> classType, Object... params) {
		return executeQuery(sql, classType, Integer.MAX_VALUE, params);
	}

	public static Object executeQuery(String sql, Class<?> classType, int returnSize, Object... params) {
		ResultSet rs = null;
		Object createdObject = null;
		returnSize = returnSize == 0 ? Integer.MAX_VALUE : returnSize;

		try (Connection conn = getConnection(); PreparedStatement ps = createPreparedStatement(conn, sql, params)) {
			rs = ps.executeQuery();
			createdObject = createObjectFromResults(rs, classType, returnSize);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return createdObject;
	}

	public static String executeQueryToJSON(String sql, Class<?> classType) {
		return new Gson().toJson(executeQuery(sql, classType, Integer.MAX_VALUE, new Object[0]), classType);
	}

	public static String executeQueryToJSON(String sql, Class<?> classType, Object... params) {
		return new Gson().toJson(executeQuery(sql, classType, Integer.MAX_VALUE, params), classType);
	}

	public static String executeQueryToJSON(String sql, Class<?> classType, int returnSize, Object... params) {
		return new Gson().toJson(executeQuery(sql, classType, returnSize, params), classType);
	}

	private static Object createObjectFromResults(ResultSet rs, Class<?> classType, int returnSize)
			throws SQLException {

		List<Object> createdObjects = new ArrayList<Object>();

		if (!rs.next()) {
			return new Object();
		} else {
			do {
				createdObjects.add(ClassesUtils.createObjectFromResultSet(rs, classType));
			} while (rs.next());
		}

		return returnSize > 1 ? createdObjects : createdObjects.get(0);
	}

	private static PreparedStatement createPreparedStatement(Connection conn, String sql, Object... params)
			throws SQLException {
		PreparedStatement ps = conn.prepareStatement(sql);

		for (int i = 0; i < params.length; i++) {
			ps.setObject(i, params[i]);
		}

		return ps;
	}

	private static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	@Override
	public void finalize() {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
