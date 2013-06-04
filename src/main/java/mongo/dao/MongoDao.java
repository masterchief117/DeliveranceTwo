package mongo.dao;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class MongoDao {

	private static final int MONGO_DB_SERVER_PORT = 27017;
	private static final String MONGO_DB_SERVER_NAME = "192.168.0.44";

	private MongoClient mongoClient;

	public MongoDao() throws UnknownHostException {
		setMongoClient(new MongoClient(new ServerAddress(MONGO_DB_SERVER_NAME,
				MONGO_DB_SERVER_PORT)));
	}

	/**
	 * @return the mongoClient
	 */
	public MongoClient getMongoClient() {
		return mongoClient;
	}

	/**
	 * @param mongoClient
	 *            the mongoClient to set
	 */
	public void setMongoClient(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}
}