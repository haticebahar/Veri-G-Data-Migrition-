package com.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.connection.Connection;
import com.sun.tools.javac.Main;
import org.bson.Document;

import java.io.File;
import java.util.*;

public class MongoConnection{

    private MongoDatabase db = null;
    List<String> columnList=new ArrayList<>();

    // public static String ipMongo;
    // public static String semaMongo;
    public MongoConnection() {
       /* Scanner scanner3 = new Scanner(System.in);
        System.out.println("Hedef MongoDB IP adresini giriniz:");
        ipMongo = scanner3.nextLine();

        Scanner scanner4 = new Scanner(System.in);
        System.out.println("MongoDB şema adını giriniz:");
        semaMongo = scanner4.nextLine();

        String mongoLabUri = "mongodb://"+ipMongo+":27017/";*/
        String mongoLabUri = "mongodb://172.21.76.134:27017/";
        MongoClientURI db_URI = new MongoClientURI(mongoLabUri);
        MongoClient db_Client = new MongoClient(db_URI);
        // db = db_Client.getDatabase(semaMongo);
        db = db_Client.getDatabase("employees");
    }

    // List<String> collectionList=new ArrayList<>();

    //MySql deki table adıyla aynı,MongoDB collectionı olusturuldu
    public void mongoCollection(String tableName){
        db.createCollection(tableName);
    }


    public void mongoColumn(List<String> listColumn){
        columnList=listColumn;
    }

    public void veriYazdır(String columnName,List<Object> veriList,String tableName){

        String col=tableName;
        //  MongoCollection collection=db.getCollection(col);
        MongoCollection<Document> mongoC=db.getCollection(col);

        for (int i=0;i<veriList.size();i++){

            List<Document> mongoNesneleri=new ArrayList<>();
            Document document=new Document(columnName,veriList.get(i));
            mongoNesneleri.add(document);
            mongoC.insertMany(mongoNesneleri);

        }
    }

    public void mapYaz(String tabloAdi, HashMap<String, Object> map) {
        MongoCollection<Document> collection = db.getCollection(tabloAdi);
        Set<String> anahtarSeti = map.keySet();

        Document document = new Document();
        for(String anahtar: anahtarSeti) {
            Object deger = map.get(anahtar);
            document.append(anahtar, deger);

        }
        collection.insertOne(document);
    }

}
