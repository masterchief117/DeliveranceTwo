package model;

import java.util.ArrayList;
import java.util.List;

public class CustomerSalesProfile {

	private int customerId;
	private List<CustomerPurchases> orders;

	public CustomerSalesProfile() {
		orders = new ArrayList<>();
	}

	/**
	 * @return the customerId
	 */
	public int getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the order
	 */
	public List<CustomerPurchases> getOrders() {
		return orders;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrders(List<CustomerPurchases> orders) {
		this.orders = orders;
	}

}
