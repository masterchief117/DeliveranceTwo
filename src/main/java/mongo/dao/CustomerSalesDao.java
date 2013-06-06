package mongo.dao;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.CustomerSalesProfile;
import model.Order;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class CustomerSalesDao {

	private MongoDao mongoDao;
	private String DATABASE = "Stachers_Customer_Sales";

	public void editCustomersSaleProfile(
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

			for (Order order : customerSalesProfile.getOrder()) {
				list.add(new BasicDBObject().append(
						"Cost",
						(((int) (100 * Double.parseDouble(order.getCost()
								.toString()))) / 100.0D)).append("Date",
						new Timestamp(order.getTimeOfSale().getMillis())));
			}
			dateCost.put("$push", new BasicDBObject("DateCost",
					new BasicDBObject().append("$each", list)));
			// ob.put("CustomerId", customerSalesProfile.getCustomerId());

			col.update(locateCustomer, dateCost, true, false);

		}
	}

	public CustomerSalesDao() throws UnknownHostException {
		mongoDao = new MongoDao();
	}
}
