package model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

public class CustomerAveragePurchases {

	private Customer customer;
	private List<YearAndDayOfYear> yearAndDayOfYear;
	private List<Integer> timeBetweenPurchases;
	private List<DateTime> dateTimeOfPurchase;

	public CustomerAveragePurchases() {
	}

	/**
	 * 
	 * @param customer
	 */
	public CustomerAveragePurchases(Customer customer) {
		this.customer = customer;
		this.yearAndDayOfYear = new ArrayList<YearAndDayOfYear>();
		this.dateTimeOfPurchase = new ArrayList<DateTime>();
	}

	/**
	 * @return the customerId
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * @return the yearAndDayOfYear
	 */
	public List<YearAndDayOfYear> getYearAndDayOfYear() {
		return yearAndDayOfYear;
	}

	/**
	 * @param yearAndDayOfYear
	 *            the yearAndDayOfYear to set
	 */
	public void setYearAndDayOfYear(List<YearAndDayOfYear> yearAndDayOfYear) {
		this.yearAndDayOfYear = yearAndDayOfYear;
	}

	/**
	 * @return the timeBetweenPurchases
	 */
	public List<Integer> getTimeBetweenPurchases() {
		return timeBetweenPurchases;
	}

	/**
	 * @param timeBetweenPurchases
	 *            the timeBetweenPurchases to set
	 */
	public void setTimeBetweenPurchases(List<Integer> timeBetweenPurchases) {
		this.timeBetweenPurchases = timeBetweenPurchases;
	}

	/**
	 * @return the dateTimeOfPurchase
	 */
	public List<DateTime> getDateTimeOfPurchase() {
		return dateTimeOfPurchase;
	}

	/**
	 * @param dateTimeOfPurchase
	 *            the dateTimeOfPurchase to set
	 */
	public void setDateTimeOfPurchase(List<DateTime> dateTimeOfPurchase) {
		this.dateTimeOfPurchase = dateTimeOfPurchase;
	}

}
