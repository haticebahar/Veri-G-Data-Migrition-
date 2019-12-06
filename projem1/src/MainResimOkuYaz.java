import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.util.ConnectionConfiguration;
import com.util.MongoConnection;

import java.io.*;
import java.sql.*;
import java.util.prefs.AbstractPreferences;

import static com.mongodb.client.model.Filters.all;
import static com.mongodb.client.model.Filters.eq;

public class MainResimOkuYaz {
    private static MongoDatabase db = null;
    private static GridFS gfsPhoto;

    public static void main(String[] args) throws IOException, SQLException {
        // TODO ilgili bilgilerin kullanicidan alinip metoda parametre gonderilmesi
        resimOkuYaz("172.21.76.134","kys","ogrenci", "ogrenciID", "1");
    }

    private static void resimOkuYaz(String host, String schema, String col, String idAlani, String idDegeri)
            throws SQLException, IOException {

        String mongoLabUri = "mongodb://"+host+":27017/";
        //String mongoLabUri = "mongodb://172.21.76.106:27017/";
        MongoClientURI db_URI = new MongoClientURI(mongoLabUri);
        MongoClient db_Client = new MongoClient(db_URI);
        db = db_Client.getDatabase(schema);
        DBCollection collection = (DBCollection) db.getCollection(col);

        BasicDBObject query = new BasicDBObject().append(idAlani, idDegeri); // WHERE name = "Jon"
        query.put(idAlani, idDegeri);
        BasicDBObject field = new BasicDBObject();
        field.put(idAlani,1);
        DBCursor cursor = collection.find(query,field);

        while (cursor.hasNext()) {
            DBObject next = cursor.next();
            String bulunan_id_degeri = (String) next.get(idAlani);
            Object resim1 = next.get("resim");
            DBCursor allData = (DBCursor) collection.findOne(bulunan_id_degeri, (DBObject) resim1);
            String dosyaYolu = "C:\\"+idAlani+"."+col+".jpg";
            FileWriter fw = null;
            BufferedWriter bw = null;
            try {
                fw = new FileWriter(dosyaYolu); // IOException'ı fırlatır
                bw = new BufferedWriter(fw);
                bw.write(String.valueOf(allData)); // IOException'ı fırlatır
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fw != null) {
                    try {
                        fw.close
                                (); // IOException'ı fırlatır
                        if(bw != null)
                            bw.close(); // IOException'ı fırlatır
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

//                    GridFS gfsPhoto = new GridFS((DB) db, "photo");
//                    GridFSDBFile imageForOutput = gfsPhoto.findOne(String.valueOf(allData));
//                    imageForOutput.writeTo("c:/"+idAlani+"."+col+".jpg");
        }
    }


//        File file = null;
//        FileOutputStream fos = null;
//        ObjectOutputStream objectOutputStream = null;
//        try {
//            file = new File(resim);
//            fos = new FileOutputStream(file);
//            objectOutputStream = new ObjectOutputStream(fos);
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            objectOutputStream.close();
//            fos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
}
