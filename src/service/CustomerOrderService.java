package service;

import java.sql.SQLException;
import java.util.List;

import model.CustomerAveragePurchases;
import model.CustomerOrder;

import dao.CustomerDao;

public class CustomerOrderService {

	private static CustomerOrderService customerService;

	private YearAndDayOfYearService yearAndDayOfYearService;
	private CustomerAveragePurchasesService customerAveragePurchasesService;

	private final CustomerDao customerDao = new CustomerDao();

	private CustomerOrderService() {

	}

	public static CustomerOrderService getInstance() {
		if (customerService == null) {
			customerService = new CustomerOrderService();
		}
		return customerService;
	}

	/**
	 * Locate the averages (for all the specified Ids
	 * @param ids
	 * @return
	 * @throws SQLException
	 */
	public String getAverages(int[] ids) throws SQLException {
		
		String questionMarks = "";
		for (int number = 0; number < ids.length; number++) {
			questionMarks += "?, ";
		}
		questionMarks = questionMarks.substring(0, questionMarks.length() - 2);
		List<CustomerOrder> customerOrders = customerDao
				.getSelectedCustomerOrderHistory(ids, questionMarks);

		return stringRepresentationOfMeanMedian(customerOrders);
	}

	private String stringRepresentationOfMeanMedian(
			List<CustomerOrder> customerOrders) {
		List<CustomerAveragePurchases> averagePurchases = getAveragesForMeanAndMedian(customerOrders);

		StringBuilder sb = new StringBuilder();
		for (CustomerAveragePurchases customerAvereragePurchase : averagePurchases) {
			sb.append("XX CustomerId = "
					+ customerAvereragePurchase.getCustomer().getCustomerId()
					+ " Mean = "
					+ yearAndDayOfYearService
							.createMean(customerAvereragePurchase
									.getTimeBetweenPurchases())
					+ " Median = "
					+ yearAndDayOfYearService
							.createMedian(customerAvereragePurchase
									.getTimeBetweenPurchases()));
		}
		String printOut = sb.toString();
		String[] array = printOut.split("XX ");
		for (String string : array) {
			System.out.println(string);
		}
		return sb.toString();
	}

	private List<CustomerAveragePurchases> getAveragesForMeanAndMedian(
			List<CustomerOrder> customerOrders) {
		yearAndDayOfYearService = YearAndDayOfYearService.getInstance();
		customerAveragePurchasesService = CustomerAveragePurchasesService
				.getInstance();
		List<CustomerAveragePurchases> purchaseHistory = customerAveragePurchasesService
				.setMultipleCustomersDates(customerOrders);
		customerAveragePurchasesService.sortPurchaseHistory(purchaseHistory);
		customerAveragePurchasesService
				.setTimeBetweenPurchases(purchaseHistory);

		return purchaseHistory;
	}
}