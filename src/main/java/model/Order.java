package model;

import java.math.BigDecimal;

import org.joda.time.DateTime;

public class Order {
	private DateTime timeOfSale;
	private BigDecimal cost;

	public Order() {
	}

	/**
	 * @return the timeOfSale
	 */
	public DateTime getTimeOfSale() {
		return timeOfSale;
	}

	/**
	 * @param timeOfSale
	 *            the timeOfSale to set
	 */
	public void setTimeOfSale(DateTime timeOfSale) {
		this.timeOfSale = timeOfSale;
	}

	/**
	 * @return the cost
	 */
	public BigDecimal getCost() {
		return cost;
	}

	/**
	 * @param cost
	 *            the cost to set
	 */
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
}
