package oracle.dao;

import java.sql.SQLException;
import java.util.List;

import sql.converter.SQLCustomerConverter;

import model.CustomerOrder;

public class CustomerDao {

	private static final String SELECT_CUSTOMER_BY_ID = "select customer_id, order_date from orders"
			+ " where customer_id IN ()" + " order by customer_id, order_date";

	private static final String EMPTY_PARAMETERS = "()";

	private OracleDao oracleDao;

	public CustomerDao() {
		// TODO Auto-generated constructor stub
	}

	public List<CustomerOrder> getSelectedCustomerOrderHistory(int[] ids,
			String questionMarks) throws SQLException {
		oracleDao = new OracleDao();
		String preparedQuery = SELECT_CUSTOMER_BY_ID.replace(EMPTY_PARAMETERS,
				"(" + questionMarks + ")");
		oracleDao.setPreparedStatement(oracleDao.getConnection().prepareStatement(
				preparedQuery));
		for (int index = 1; index <= ids.length; index++) {
			oracleDao.getPreparedStatement().setInt(index, ids[index - 1]);
		}
		oracleDao.setResultSet(oracleDao.getPreparedStatement().executeQuery());
		
		return SQLCustomerConverter.getCustomerOrders(oracleDao.getResultSet());
	}
}
