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

public class CustomerSalesDao {

	private MongoDao mongoDao;
	private String DATABASE = "Stachers_Customer_Sales";

	public void pushCustomersSaleProfile(
			List<CustomerSalesProfile> customerSalesProfiles)
			throws IOException {
		// TODO add comments!
		mongoDao = new MongoDao();
		DB db = mongoDao.getMongoClient().getDB(DATABASE);
		DBCollection col = db.getCollection("sales");
		for (CustomerSalesProfile customerSalesProfile : customerSalesProfiles) {
			BasicDBObject locateCustomer = new BasicDBObject();
			locateCustomer.put("customer_id",
					customerSalesProfile.getCustomerId());

			BasicDBObject dateCost = new BasicDBObject();
			List<BasicDBObject> list = new ArrayList<>();

			for (CustomerPurchases order : customerSalesProfile.getOrders()) {
				list.add(new BasicDBObject()
						.append("Cost",
								(((int) (100 * Double.parseDouble(order
										.getTotalCostOfPurchase().toString()))) / 100.0D))
						.append("Date", order.getTimeOfSale()));
			}
			dateCost.put("$push", new BasicDBObject("DateCost",
					new BasicDBObject().append("$each", list)));

			col.update(locateCustomer, dateCost, true, false);
			
		}
	}

	public CustomerSalesDao() throws UnknownHostException {
		mongoDao = new MongoDao();
	}
}
