package service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import oracle.dao.CustomerDao;

import model.CustomerAveragePurchases;
import model.CustomerOrder;
import model.CustomerSalesProfile;
import model.Order;
import mongo.dao.CustomerSalesDao;

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
	 * 
	 * @param ids
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public String getAverages(int[] ids) throws SQLException, IOException {

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
			List<CustomerOrder> customerOrders) throws IOException {
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
			List<CustomerOrder> customerOrders) throws IOException {
		yearAndDayOfYearService = YearAndDayOfYearService.getInstance();
		customerAveragePurchasesService = CustomerAveragePurchasesService
				.getInstance();
		List<CustomerAveragePurchases> purchaseHistory = customerAveragePurchasesService
				.setMultipleCustomersDates(customerOrders);
		customerAveragePurchasesService.sortPurchaseHistory(purchaseHistory);
		customerAveragePurchasesService
				.setTimeBetweenPurchases(purchaseHistory);
		// TODO
		CustomerSalesProfile customerProfile;
		List<CustomerSalesProfile> list = new ArrayList<CustomerSalesProfile>();
		Order order;
		for (CustomerAveragePurchases purchase : purchaseHistory) {
			customerProfile = new CustomerSalesProfile();
			customerProfile.setCustomerId(purchase.getCustomer()
					.getCustomerId());
			customerProfile.setOrder(new ArrayList<Order>());
			for (DateTime time : purchase.getDateTimeOfPurchase()) {
				if (customerProfile.getOrder() == null) {
					customerProfile.setOrder(new ArrayList<Order>());
				}
				order = new Order();
				order.setTimeOfSale(time);
				order.setCost(new BigDecimal(Math.random() * 2050));
				customerProfile.getOrder().add(order);
			}
			list.add(customerProfile);
		}
		CustomerSalesDao sales = new CustomerSalesDao();
		sales.editCustomersSaleProfile(list);
		return purchaseHistory;
	}
}