package org.zgg.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class InsertDocument {

	public static void main(String[] args) {
		try{

			MongoClient mongoClient =new MongoClient("localhost",27017);
			MongoDatabase mongodatabase=mongoClient.getDatabase("ZGG");
			System.out.println("connect to database successfully");
			
			MongoCollection<Document> collection=mongodatabase.getCollection("test");
			System.out.println("choosing test successfully");

			/*
			 * 插入文档:
			 *   1. 创建文档 org.bson.Document 参数为key-value的格式
			 *   2. 创建文档集合List<Document>
			 *   3. 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>)
			 *      插入单个文档可以用 mongoCollection.insertOne(Document)
			 * */

			Document document = new  Document("title","MongoDB").
					append("description", "database").
					append("likes", 100).append("by", "Fly");
			List<Document> documents= new ArrayList<Document>();
			documents.add(document);
			collection.insertMany(documents);
			System.out.println("document inserted successfully");

		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
	}

}
