package oracle.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleDriver;

public class OracleDao {

	private static final String URL_TO_ORACLE_DB = "jdbc:oracle:thin:@192.168.0.44:1521:MongoDemo";
	private static final String OE_USERNAME = "oe";
	private static final String OE_PASSWORD = "oe";

	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	/**
	 * Protected to limit access to this package.
	 * 
	 * @throws SQLException
	 */
	protected OracleDao() throws SQLException {
		DriverManager.registerDriver(new OracleDriver());
		connection = DriverManager.getConnection(URL_TO_ORACLE_DB, OE_USERNAME,
				OE_PASSWORD);
	}

	/**
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * @param connection
	 *            the connection to set
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * @return the preparedStatement
	 */
	public PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}

	/**
	 * @param preparedStatement
	 *            the preparedStatement to set
	 */
	public void setPreparedStatement(PreparedStatement preparedStatement) {
		this.preparedStatement = preparedStatement;
	}

	/**
	 * @return the resultSet
	 */
	public ResultSet getResultSet() {
		return resultSet;
	}

	/**
	 * @param resultSet
	 *            the resultSet to set
	 */
	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

}
