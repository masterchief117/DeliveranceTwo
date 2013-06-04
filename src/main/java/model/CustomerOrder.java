package model;

import org.joda.time.DateTime;

public class CustomerOrder {

	private Customer customer;
	private DateTime orderDate;

	public CustomerOrder() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the orderDate
	 */
	public DateTime getOrderDate() {
		return orderDate;
	}

	/**
	 * @param orderDate
	 *            the orderDate to set
	 */
	public void setOrderDate(DateTime orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customer
	 *            the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
