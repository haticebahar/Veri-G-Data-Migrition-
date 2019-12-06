
import com.util.MongoConnection;

import com.util.ConnectionConfiguration;

import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        ConnectionConfiguration connectionConfiguration=new ConnectionConfiguration();
        MongoConnection mongoConnection=new MongoConnection();

        //List<String> tablolar = connectionConfiguration.tablesMethod(MongoConnection.semaMongo, null, null, null);
        List<String> tablolar = connectionConfiguration.tablesMethod("employees", null, null, null);

        for(String tabloAdi: tablolar) {
            List<HashMap<String, Object>> liste = connectionConfiguration.veriCekHepsi(tabloAdi);
            for(HashMap<String, Object> map: liste) {
                mongoConnection.mapYaz(tabloAdi, map);
            }

        }

        new MainResimOkuYaz();
    }
}
