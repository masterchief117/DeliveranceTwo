package sql.converter;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	/**
	 * Gets the customer orders from the DB, used as a "RowMapper". For now, we
	 * do not have cost associated with the user, so it is created randomly,
	 * with a maximum of one thousand and mininum of zero
	 * 
	 * @param customerOrderResults
	 * @return
	 * @throws SQLException
	 */
	public static List<CustomerSalesProfile> getCustomerOrders(
			ResultSet customerOrderResults) throws SQLException {
		// holds all the cusomter orders
		List<CustomerSalesProfile> customerOrders = new ArrayList<>();
		// their purchases
		CustomerPurchases customerPurchases;
		// their profile (which contains a list of their purchases and
		// customerId)
		CustomerSalesProfile customerSalesProfile;
		boolean containsCustomer = false;
		int customerId = 0;
		// iterate through all the result sets
		while (customerOrderResults.next()) {
			// create a new purchase record
			customerPurchases = new CustomerPurchases();
			// store their Id into an integer, used later.
			customerId = customerOrderResults.getInt(CUSTOMER_ID);
			// add the purchase info!
			customerPurchases.setTimeOfSale(customerOrderResults.getDate(
					ORDER_DATE).getTime());
			customerPurchases.setTotalCostOfPurchase(new BigDecimal(
					((int) (Math.random() * 10000) / 10.0)));
			// iterate through the list of users we have already pulled. this
			// loops backwards, the db call should produce an ordered list.
			for (int indexCustomerOrders = customerOrders.size() - 1; indexCustomerOrders > -1; indexCustomerOrders--) {
				// check to see if customer exists.
				if (customerId == customerOrders.get(indexCustomerOrders)
						.getCustomerId()) {
					// add to the purchase orders
					containsCustomer = true;
					customerOrders.get(indexCustomerOrders).getOrders()
							.add(customerPurchases);
					break;
				}
			}
			// if the customer did not exist, make a new one!
			if (!containsCustomer) {
				// and insert the data on the new customer! Ya! new sale.

				customerSalesProfile = new CustomerSalesProfile();
				customerSalesProfile.setCustomerId(customerId);
				customerSalesProfile.getOrders().add(customerPurchases);
				customerOrders.add(customerSalesProfile);
			}
			// and re-loop!
			containsCustomer = false;
		}
		// all the new customer orders captured
		return customerOrders;
	}
}
