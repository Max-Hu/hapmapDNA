import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;


public class MongoDBDriver {

    private MongoDatabase db;

    public MongoDBDriver() {
        MongoClient mongoClient = new MongoClient();
        db = mongoClient.getDatabase("DNA");
    }

    public void insertData(Document object){
        db.getCollection("base_data").insertOne(object);
    }

    public ArrayList<Document> findData(){
        FindIterable<Document> iterable = db.getCollection("base_data").find(
                new Document("rsID", "rs11240767"));

        final ArrayList<Document> list = new ArrayList<Document>();
        iterable.forEach(new Block<Document>() {
            public void apply(final Document document) {
                list.add(document);
            }
        });
        return list;
    }


    public static void main(String[] args) {
        MongoDBDriver driver = new MongoDBDriver();
        ArrayList<Document> list = driver.findData();
        System.out.println(list);

    }

}
