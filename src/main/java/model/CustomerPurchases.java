package model;

import java.math.BigDecimal;

public class CustomerPurchases {

	private long timeOfSale;
	private BigDecimal totalCostOfPurchase;

	/**
	 * @return the totalCostOfPurchase
	 */
	public BigDecimal getTotalCostOfPurchase() {
		return totalCostOfPurchase;
	}

	/**
	 * @param totalCostOfPurchase
	 *            the totalCostOfPurchase to set
	 */
	public void setTotalCostOfPurchase(BigDecimal totalCostOfPurchase) {
		this.totalCostOfPurchase = totalCostOfPurchase;
	}

	/**
	 * @return the timeOfSale
	 */
	public long getTimeOfSale() {
		return timeOfSale;
	}

	/**
	 * @param timeOfSale
	 *            the timeOfSale to set
	 */
	public void setTimeOfSale(long timeOfSale) {
		this.timeOfSale = timeOfSale;
	}

}
