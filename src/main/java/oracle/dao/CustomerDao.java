package oracle.dao;

import java.sql.SQLException;
import java.util.List;

import sql.converter.SQLCustomerConverter;

import model.CustomerSalesProfile;

public class CustomerDao {
	// SQL statement provided by Dale
	private static final String SELECT_CUSTOMER_BY_ID = "select customer_id, order_date from orders"
			+ " where customer_id IN ()" + " order by customer_id, order_date";
	// needed to place the question marks properly
	private static final String EMPTY_PARAMETERS = "()";

	private OracleDao oracleDao;

	public CustomerDao() {
	}

	public List<CustomerSalesProfile> getSelectedCustomerOrderHistory(
			int[] ids, String questionMarks) throws SQLException {
		// open connection to oracleDAO. Probably will change this later. Got a
		// better idea. Have the connection in OracleDao, all other objects in
		// local DAO, is the idea.
		oracleDao = new OracleDao();
		// amend the DB call string
		String preparedQuery = SELECT_CUSTOMER_BY_ID.replace(EMPTY_PARAMETERS,
				"(" + questionMarks + ")");
		// open up the preparedstatement
		oracleDao.setPreparedStatement(oracleDao.getConnection()
				.prepareStatement(preparedQuery));
		//
		for (int index = 1; index <= ids.length; index++) {
			oracleDao.getPreparedStatement().setInt(index, ids[index - 1]);
		}
		// QUERY!!
		oracleDao.setResultSet(oracleDao.getPreparedStatement().executeQuery());
		// use the row mapper to return a list of Customers who have made
		// purchases.
		return SQLCustomerConverter.getCustomerOrders(oracleDao.getResultSet());
	}
}
