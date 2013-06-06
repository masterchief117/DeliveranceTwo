package model;

import java.util.List;

public class CustomerSalesProfile {

	private int customerId;
	private List<Order> order;

	public CustomerSalesProfile() {
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
	public List<Order> getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(List<Order> order) {
		this.order = order;
	}

}
