package org.zgg.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Collection {
	public static void main(String[] args) {
		try{

			MongoClient mongoClient =new MongoClient("localhost",27017);
			MongoDatabase mongodatabase=mongoClient.getDatabase("ZGG");
			System.out.println("connect to database successfully");

			mongodatabase.createCollection("test");
			System.out.println("creating collection successfully");

			MongoCollection<Document> collection=mongodatabase.getCollection("test");
			System.out.println("choosing test successfully");
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
	}
}
