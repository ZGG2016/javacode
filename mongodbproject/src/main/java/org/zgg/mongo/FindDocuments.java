package org.zgg.mongo;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class FindDocuments {

	public static void main(String[] args) {
		try{

			MongoClient mongoClient =new MongoClient("localhost",27017);
			MongoDatabase mongodatabase=mongoClient.getDatabase("ZGG");
			System.out.println("Connect to database successfully");

			MongoCollection<Document> collection=mongodatabase.getCollection("test");
			System.out.println("choosing test successfully");

			/*
			 * 检索所有文档:
			 *   1. 获取迭代器FindIterable<Document>
			 *   2. 获取游标MongoCursor<Document>
			 *   3. 通过游标遍历检索出的文档集合
			 * */

			FindIterable<Document> findIterable = collection.find();
			MongoCursor<Document> mongoCusor = findIterable.iterator();
			while(mongoCusor.hasNext()){
				System.out.println(mongoCusor.next());
			}
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
	}

}
