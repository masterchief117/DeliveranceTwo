package service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.joda.time.DateTime;

import model.CustomerAveragePurchases;
import model.CustomerOrder;
import model.YearAndDayOfYear;

public class CustomerAveragePurchasesService {

	private static CustomerAveragePurchasesService customerAveragePurchasesService;

	private YearAndDayOfYearService yearAndDayOfYearService;

	private CustomerAveragePurchasesService() {
	}

	public static CustomerAveragePurchasesService getInstance() {
		if (customerAveragePurchasesService == null) {
			customerAveragePurchasesService = new CustomerAveragePurchasesService();
		}
		return customerAveragePurchasesService;
	}

	public List<CustomerAveragePurchases> setMultipleCustomersDates(
			List<CustomerOrder> customerOrders) {
		TreeMap<Integer, CustomerAveragePurchases> customerOrderTree = new TreeMap<>();
		for (CustomerOrder customerOrder : customerOrders) {
			// create key if one does not exist
			if (!customerOrderTree.containsKey(customerOrder.getCustomer()
					.getCustomerId())) {
				customerOrderTree.put(customerOrder.getCustomer()
						.getCustomerId(),
						createNewAveragePurchase(customerOrder));
			}
			List<YearAndDayOfYear> cust = customerOrderTree.get(
					customerOrder.getCustomer().getCustomerId())
					.getYearAndDayOfYear();
			List<DateTime> timeOfYeah = customerOrderTree.get(
					customerOrder.getCustomer().getCustomerId())
					.getDateTimeOfPurchase();
			timeOfYeah.add(customerOrder.getOrderDate());
			cust.add(new YearAndDayOfYear(customerOrder.getOrderDate()
					.getYear(), customerOrder.getOrderDate().getDayOfYear()));
		}
		customerOrderTree.navigableKeySet();
		List<CustomerAveragePurchases> purchaseHistory = new ArrayList<>();
		for (int key : customerOrderTree.keySet()) {
			purchaseHistory.add(customerOrderTree.get(key));
		}
		return purchaseHistory;
	}

	public CustomerAveragePurchases createNewAveragePurchase(
			CustomerOrder customerOrder) {
		return new CustomerAveragePurchases(customerOrder.getCustomer());
	}

	public void sortPurchaseHistory(
			List<CustomerAveragePurchases> purchaseHistory) {
		yearAndDayOfYearService = YearAndDayOfYearService.getInstance();
		for (CustomerAveragePurchases customersPurchase : purchaseHistory) {
			yearAndDayOfYearService.sortYearAndDay(customersPurchase
					.getYearAndDayOfYear());
		}

	}

	public void setTimeBetweenPurchases(
			List<CustomerAveragePurchases> purchaseHistory) {
		for (CustomerAveragePurchases purchase : purchaseHistory) {
			purchase.setTimeBetweenPurchases(createTimeInbetween(purchase
					.getYearAndDayOfYear()));

		}
	}

	private List<Integer> createTimeInbetween(
			List<YearAndDayOfYear> yearAndDayOfYear) {
		List<Integer> timeBetweenPurchases = new ArrayList<>();
		int differenceInDays = 0;
		int yearA = 0;
		int yearB = 0;
		int dayA = 0;
		int dayB = 0;
		for (int index = 0; index < yearAndDayOfYear.size() - 1; index++) {
			yearA = yearAndDayOfYear.get(index).getYear();
			yearB = yearAndDayOfYear.get(index + 1).getYear();
			dayA = yearAndDayOfYear.get(index).getDay();
			dayB = yearAndDayOfYear.get(index + 1).getDay();
			differenceInDays = 0;
			if (yearA == yearB) {
				differenceInDays = dayB - dayA;
			} else {
				for (int year = 0; year < yearB - yearA; year++) {
					if (year == 0 && yearIsLeapYear(yearA)) {
						differenceInDays = 366 - dayA;
					} else if (year == 0) {
						differenceInDays = 365 - dayA;
					} else if (yearIsLeapYear(yearA + year)) {
						differenceInDays += 366;
					} else {
						differenceInDays += 365;
					}
				}
				differenceInDays += dayB;
			}
			timeBetweenPurchases.add(differenceInDays);
		}
		return timeBetweenPurchases;
	}

	private boolean yearIsLeapYear(int yearA) {
		return ((yearA % 4 == 0 && yearA % 100 != 0) || yearA % 400 == 0) ? true
				: false;
	}
}
