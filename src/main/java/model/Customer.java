package model;

public class Customer {

	private int customerId;

	public Customer() {
	}

	public Customer(int customerId) {
		this.customerId = customerId;
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

}
