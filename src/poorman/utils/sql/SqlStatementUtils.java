package poorman.utils.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import poorman.utils.bean.BeanUtils;

public class SqlStatementUtils {

	private ResultSet rs;

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

	/**
	 * Returns a ResultSet based upon the queried statement
	 *
	 * @param sql = the String SQL statement
	 * @return ResultSet
	 * 
	 * @author Ryan Poorman
	 */
	public static Object executeQuery(String sql) {
		return executeQuery(sql, new Object[0]);
	}
	
	/**
	 * Returns a ResultSet based upon the queried statement
	 *
	 * @param sql = the String SQL statement
	 * @param params = an array of parameters that will be used in a PreparedStatement
	 * @return ResultSet
	 * 
	 * @author Ryan Poorman
	 */
	public static Object executeQuery(String sql, Object... params) {
		ResultSet rs = null;
		
		try (Connection conn = getConnection(); PreparedStatement ps = createPreparedStatement(conn, sql, params)) {
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	
	/**
	 * Returns a new instance of the classType parameter based upon the queried statement
	 * in the form of a list with each element of the list being an object of Type T
	 * 
	 * @param sql = the String SQL statement
	 * @param classType = the Type of Class that will be used to instantiate the objects
	 * @return List<T>
	 * 
	 * @author Ryan Poorman
	 */
	public static Object executeQuery(String sql, Class<?> classType) {
		return executeQuery(sql, classType, Integer.MAX_VALUE, new Object[0]);
	}
	
	
	/**
	 * Returns a new instance of the classType parameter based upon the queried statement
	 * in the form of a list with each element of the list being an object of Type T
	 * 
	 * @param sql = the String SQL statement
	 * @param classType = the Type of Class that will be used to instantiate the objects
	 * @param params = an array of parameters that will be used in a PreparedStatement
	 * @return List<T>
	 * 
	 * @author Ryan Poorman
	 */
	public static Object executeQuery(String sql, Class<?> classType, Object... params) {
		return executeQuery(sql, classType, Integer.MAX_VALUE, params);
	}
	
	
	/**
	 * Returns a new instance of the classType parameter based upon the queried statement
	 * in the form of a list with each element of the list being an object of Type T
	 * 
	 * @param sql = the String SQL statement
	 * @param classType = the Type of Class that will be used to instantiate the objects
	 * @param returnSize = optional functionality 
	 * @return List<T>
	 * 
	 * @author Ryan Poorman
	 */
	public static Object executeQuery(String sql, Class<?> classType, int returnSize) {
		return executeQuery(sql, classType, returnSize, new Object[0]);
	}

	
	/**
	 * Returns a new instance of the classType parameter based upon the queried statement
	 * in the form of a list with each element of the list being an object of Type T
	 * 
	 * @param sql = the String SQL statement
	 * @param classType = the Type of Class that will be used to instantiate the objects
	 * @param returnSize = optional functionality 
	 * @param params = an array of parameters that will be used in a PreparedStatement
	 * @return List<T>
	 * 
	 * @author Ryan Poorman
	 */
	public static <T> List<T> executeQuery(String sql, Class<?> classType, int returnSize, Object... params) {
		ResultSet rs = null;
		List<T> createdObjects = null;
		returnSize = returnSize == 0 ? Integer.MAX_VALUE : returnSize;

		try (Connection conn = getConnection(); PreparedStatement ps = createPreparedStatement(conn, sql, params)) {
			rs = ps.executeQuery();
			createdObjects.addAll(createObjectFromResultSet(rs, classType, returnSize));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return createdObjects;
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

	@SuppressWarnings("unchecked")
	private static <T> List<T> createObjectFromResultSet(ResultSet rs, Class<?> classType, int returnSize)
			throws SQLException {

		List<T> createdObjects = new ArrayList<>();

		if (!rs.next() || returnSize < 1) {
			return new ArrayList<>();
		} else {
			ResultSetMetaData rsmd = rs.getMetaData();
			do {
				createdObjects.add((T) RelationalMapper.createMappedObject(classType, rsmd, rs));
			} while (rs.next());
		}

		return createdObjects;
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
