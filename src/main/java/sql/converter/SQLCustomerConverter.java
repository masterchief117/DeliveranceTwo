package sql.converter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import model.Customer;
import model.CustomerOrder;

/**
 * In replace of a RowMapper provided by Spring.
 * 
 * @author bstewart
 * 
 */
public class SQLCustomerConverter {

	private static final String CUSTOMER_ID = "customer_id";

	private SQLCustomerConverter() {
	}

	public static List<CustomerOrder> getCustomerOrders(
			ResultSet customerOrderResults) throws SQLException {
		List<CustomerOrder> customerOrders = new ArrayList<>();
		CustomerOrder customerOrder;
		while (customerOrderResults.next()) {
			customerOrder = new CustomerOrder();
			customerOrder.setCustomer(new Customer(customerOrderResults
					.getInt(CUSTOMER_ID)));
			customerOrder.setOrderDate(new DateTime(customerOrderResults
					.getDate("order_date")));
			customerOrders.add(customerOrder);
		}
		return customerOrders;
	}
}
