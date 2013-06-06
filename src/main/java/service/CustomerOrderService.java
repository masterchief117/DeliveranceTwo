package service;

import java.io.IOException;
import java.sql.SQLException;

import oracle.dao.CustomerDao;
import mongo.dao.CustomerSalesDao;

/**
 * service layer for CustomerOrderService
 * 
 * @author bstewart
 * 
 */
public class CustomerOrderService {

	private static CustomerOrderService customerService;

	private final CustomerDao customerDao = new CustomerDao();
	private final CustomerSalesDao customerSalesDao;

	/**
	 * Is a singleton!
	 * 
	 * @throws IOException
	 */
	private CustomerOrderService() throws IOException {
		customerSalesDao = new CustomerSalesDao();
	}

	/**
	 * A singleton method call.
	 * 
	 * @return the singleton instance
	 * @throws IOException
	 */
	public static CustomerOrderService getInstance() throws IOException {
		if (customerService == null) {
			customerService = new CustomerOrderService();
		}
		return customerService;
	}

	/**
	 * Grabs all the user information from SQL and stores in MONGO.
	 * 
	 * @param ids
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public void getAverages(int[] ids) throws SQLException, IOException {

		// for the preparedStatement, needs those question marks!
		String questionMarks = "";
		for (int number = 0; number < ids.length; number++) {
			questionMarks += "?, ";
		}
		// remove the ", "
		questionMarks = questionMarks.substring(0, questionMarks.length() - 2);

		// queries the SQL database, then pushes the results to MongoDB
		customerSalesDao.pushCustomersSaleProfile(customerDao
				.getSelectedCustomerOrderHistory(ids, questionMarks));
	}
}