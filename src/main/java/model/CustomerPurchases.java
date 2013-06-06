package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class CustomerPurchases {

	private Timestamp timeOfSale;
	private BigDecimal totalCostOfPurchase;

	public Timestamp getTimeOfSale() {
		return timeOfSale;
	}

	public void setTimeOfSale(Timestamp timeOfSale) {
		this.timeOfSale = timeOfSale;

	}

	public BigDecimal getTotalCostOfPurchase() {
		return totalCostOfPurchase;
	}

	public void setTotalCostOfPurchase(BigDecimal totalCostOfPurchase) {
		this.totalCostOfPurchase = totalCostOfPurchase;

	}

}
