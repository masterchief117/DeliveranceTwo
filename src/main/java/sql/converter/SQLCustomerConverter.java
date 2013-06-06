package sql.converter;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.CustomerPurchases;
import model.CustomerSalesProfile;

/**
 * In replace of a RowMapper provided by Spring.
 * 
 * @author bstewart
 * 
 */
public class SQLCustomerConverter {

	private static final String CUSTOMER_ID = "customer_id";
	private static final String ORDER_DATE = "order_date";

	private SQLCustomerConverter() {
	}

	public static List<CustomerSalesProfile> getCustomerOrders(
			ResultSet customerOrderResults) throws SQLException {
		List<CustomerSalesProfile> customerOrders = new ArrayList<>();
		CustomerPurchases customerPurchases;
		CustomerSalesProfile customerSalesProfile;
		boolean containsCustomer = false;
		int customerId = 0;
		while (customerOrderResults.next()) {
			customerPurchases = new CustomerPurchases();
			customerId = customerOrderResults.getInt(CUSTOMER_ID);
			customerPurchases.setTimeOfSale(new Timestamp(customerOrderResults
					.getDate(ORDER_DATE).getTime()));
			customerPurchases.setTotalCostOfPurchase(new BigDecimal(
					((int) (Math.random() * 10000) / 10.0)));
			for (int indexCustomerOrders = 0; indexCustomerOrders < customerOrders
					.size(); indexCustomerOrders++) {
				if (customerId == customerOrders.get(indexCustomerOrders)
						.getCustomerId()) {
					containsCustomer = true;
					customerOrders.get(indexCustomerOrders).getOrders()
							.add(customerPurchases);
					break;
				}
			}
			if (!containsCustomer) {
				customerSalesProfile = new CustomerSalesProfile();
				customerSalesProfile.setCustomerId(customerId);
				customerSalesProfile.getOrders().add(customerPurchases);
				customerOrders.add(customerSalesProfile);
			}
			containsCustomer = false;
		}
		
		return customerOrders;
	}
}
