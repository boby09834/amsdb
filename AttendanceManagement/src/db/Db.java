package db;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Db {

	private static final String DB_NAME = "amsdb";
	

	static MongoDatabase db;

	private Db() {
	}

	@SuppressWarnings("resource")
	public static MongoDatabase getDatabase() {
		if (db == null) {
			
			com.mongodb.client.MongoClient mongoClient =MongoClients.create("mongodb://amsdb:jore70123@cluster0-shard-00-00.wkbn2.mongodb.net:27017,cluster0-shard-00-01.wkbn2.mongodb.net:27017,cluster0-shard-00-02.wkbn2.mongodb.net:27017/myFirstDatabase?ssl=true&replicaSet=atlas-h2cd6g-shard-0&authSource=admin&retryWrites=true&w=majority");
			db = mongoClient.getDatabase(DB_NAME);
		}
		return db;
	}
	
	//MongoClient mongoClient = new MongoClient(DB_HOST, DB_PORT);
	private static final String DB_HOST = "localhost";
	private static final int DB_PORT = 27017;

}
