package service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import oracle.dao.CustomerDao;

import model.CustomerSalesProfile;
import mongo.dao.CustomerSalesDao;

public class CustomerOrderService {

	private static CustomerOrderService customerService;

	private final CustomerDao customerDao = new CustomerDao();
	private final CustomerSalesDao customerSalesDao;

	private CustomerOrderService() throws IOException {
		customerSalesDao = new CustomerSalesDao();
	}

	public static CustomerOrderService getInstance() throws IOException {
		if (customerService == null) {
			customerService = new CustomerOrderService();
		}
		return customerService;
	}

	/**
	 * Locate the averages (for all the specified Ids
	 * 
	 * @param ids
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public void getAverages(int[] ids) throws SQLException, IOException {

		String questionMarks = "";
		for (int number = 0; number < ids.length; number++) {
			questionMarks += "?, ";
		}
		questionMarks = questionMarks.substring(0, questionMarks.length() - 2);
		List<CustomerSalesProfile> customerOrders = customerDao
				.getSelectedCustomerOrderHistory(ids, questionMarks);

		customerSalesDao.pushCustomersSaleProfile(customerOrders);
	}

}