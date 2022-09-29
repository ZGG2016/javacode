package org.zgg.mongo;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class DeleteDocument {

	public static void main(String[] args) {
		try{

			MongoClient mongoClient =new MongoClient("localhost",27017);
			MongoDatabase mongodatabase=mongoClient.getDatabase("ZGG");
			System.out.println("Connect to database successfully");

			MongoCollection<Document> collection=mongodatabase.getCollection("test");
			System.out.println("choosing test successfully");

			//删除符合条件的第一个文档
			collection.deleteOne(Filters.eq("likes",200));
			//删除所有符合条件的文档
			collection.deleteMany(Filters.eq("likes",200));
			//检索查看结果
			FindIterable<Document> findIterable=collection.find();
			MongoCursor<Document> mongoCursor=findIterable.iterator();
			while(mongoCursor.hasNext()){
				System.out.println(mongoCursor.next());
			}
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}

	}

}
