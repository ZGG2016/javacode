package org.zgg.mongo;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class UpdateDocument {

	public static void main(String[] args) {
		try{

			MongoClient mongoClient =new MongoClient("localhost",27017);
			MongoDatabase mongodatabase=mongoClient.getDatabase("ZGG");
			System.out.println("Connect to database successfully");

			MongoCollection<Document> collection=mongodatabase.getCollection("test");
			System.out.println("choosing test successfully");

			/*
			* 更新文档:
			*    将文档中likes=100的文档修改为likes=200
			*
			* */
			collection.updateMany(Filters.eq("likes",100),
					new Document("$set", new Document("likes", 200)));
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
