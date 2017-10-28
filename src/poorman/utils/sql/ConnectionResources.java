package poorman.utils.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionResources {

	private Connection conn;
	private ResultSet rs;
	private PreparedStatement ps;

	@Override
	public void finalize() {

		try {
			if (conn != null)
				conn.close();
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public Connection getConnection() {
		return conn;
	}

	public void setResultSet(ResultSet rs) {
		this.rs = rs;
	}

	public ResultSet getResultSet() {
		return rs;
	}

	public void setPreparedStatement(PreparedStatement ps) {
		this.ps = ps;
	}

	public PreparedStatement getPreparedStatement() {
		return ps;
	}

}
