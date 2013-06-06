package controller;

import java.io.IOException;
import java.sql.SQLException;

import service.CustomerOrderService;

/**
 * Controller for the testing. Either send in a string of args to the console,
 * or use default.
 * 
 * @author bstewart
 * 
 */
public class CollectMeanMedian {

	/**
	 * DEFAULT_IDS, provided by Dale.
	 */
	private static final int[] DEFAULT_IDS = { 101, 102, 103, 104, 144, 145,
			146, 150 };

	/**
	 * No-Args constructor
	 */
	public CollectMeanMedian() {
	}

	/**
	 * main method. Run from console. Push in the ids you want to check!
	 * 
	 * @param listOfIds
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void main(String[] listOfIds) throws SQLException,
			IOException {
		CustomerOrderService customerService = CustomerOrderService
				.getInstance();
		int[] ids;
		// if there are submitted from the console, push them into ids!
		if (listOfIds.length > 0) {
			ids = new int[listOfIds.length];
			for (int index = 0; index < listOfIds.length; index++) {
				try {
					ids[index] = Integer.parseInt(listOfIds[index]);
				} catch (NumberFormatException badNum) {
					// if integer not supplied.
					System.out
							.println("enter the ids as integers, seperated by space!!");
					System.exit(0);
				}
			}
		} else {
			ids = DEFAULT_IDS;
		}
		// runs the programs.
		customerService.getAverages(ids);
	}
}
