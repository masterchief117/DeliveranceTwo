package mongo.dao;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import model.CustomerSalesProfile;
import model.CustomerPurchases;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

import com.mongodb.MapReduceCommand;

import com.mongodb.QueryBuilder;

public class CustomerSalesDao {

	private MongoDao mongoDao;
	private String DATABASE = "Stachers_Customer_Sales";

	/**
	 * Currently setups up the documents. Pushes each customer one at a time.
	 * After all the pushes have been made to MongoDB, it runs the MapReducer.
	 * 
	 * @param customerSalesProfiles
	 * @throws IOException
	 */
	public void pushCustomersSaleProfile(
			List<CustomerSalesProfile> customerSalesProfiles)
			throws IOException {
		// create a new connection probably will change to a singleton in later
		// iterations
		mongoDao = new MongoDao();
		// Get the DB
		DB db = mongoDao.getMongoClient().getDB(DATABASE);
		// get the Collection
		DBCollection col = db.getCollection("sales");
		// for each customer..
		for (CustomerSalesProfile customerSalesProfile : customerSalesProfiles) {
			// make a query
			BasicDBObject locateCustomer = new BasicDBObject();
			locateCustomer.put("customerId",
					customerSalesProfile.getCustomerId());
			// create the outer document to the inner document
			BasicDBObject dateCost = new BasicDBObject();
			// the inner documents
			List<BasicDBObject> list = new ArrayList<>();
			// for each order the customer has..
			for (CustomerPurchases order : customerSalesProfile.getOrders()) {
				// add it to the list!!
				list.add(new BasicDBObject()
						.append("cost",
								(((int) (100 * Double.parseDouble(order
										.getTotalCostOfPurchase().toString()))) / 100.00D))
						.append("date", order.getTimeOfSale()));
			}
			// read this as {$push : { dateCost : {$each : [{value1, value2},
			// {value1, value2}, {value1, value2}, {value1, value2}] } } }
			dateCost.append("$push", new BasicDBObject("dateCost",
					new BasicDBObject().append("$each", list)));
			// read this as {costumerId : theId, dateCost :[{date1, cost1},
			// {date2, cost2}, {date3, cost3}, {date4, cost4}]}
			// the first boolean is for upsert (update, if not present, insert)
			col.update(locateCustomer, dateCost, true, true);

		}

		// a list of customerIds for the query
		int[] customerIds = new int[customerSalesProfiles.size()];
		for (int index = 0; index < customerSalesProfiles.size(); index++) {
			customerIds[index] = customerSalesProfiles.get(index)
					.getCustomerId();
		}
		// http://api.mongodb.org/java/current/
		// that link will have more details about what is going on here.
		// YOU NEED TO USE QUERYBUILDER for trickier db calls, ie $in
		MapReduceCommand mapReduce = new MapReduceCommand(col, MongoDao.MAP,
				MongoDao.REDUCE, "a_test", MapReduceCommand.OutputType.MERGE,
				QueryBuilder.start().put("customerId").in(customerIds).get());

		// executes teh mapReduce from above.
		col.mapReduce(mapReduce);

	}

	public CustomerSalesDao() throws UnknownHostException {
		mongoDao = new MongoDao();
	}
}
