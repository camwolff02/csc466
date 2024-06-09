package labs;

import DocumentClasses.CosineDistance;
import DocumentClasses.DocumentCollection;
import DocumentClasses.TextVector;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Lab2 {
    public static final String PATH = "src/files/" ;

    public static DocumentCollection documents;
    public static DocumentCollection queries;

    public static void main(String[] args) {
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(PATH + "docvector"))) {
            // load data from the binary file
            documents = (DocumentCollection)stream.readObject();

            // initialize the queries variable
            queries = new DocumentCollection(PATH + "queries.txt", "query");

            // call normalize() on documents and queries
            documents.normalize(documents);
            queries.normalize(documents);

            // compute the top 20 documents for each query using the TF-IDF algorithm
            // and cosine similarity
            HashMap<Integer, ArrayList<Integer>> queryNumToTop20 = new HashMap<>();
            var dist = new CosineDistance();

            for (var queryEntry : queries.getEntrySet()) {
               queryNumToTop20.put(queryEntry.getKey(), queryEntry.getValue().findClosestDocuments(documents, dist, 20));
            }

            // print the 20 most relevant documents for each query
            for (var entry : queryNumToTop20.entrySet()) {
                System.out.print("documents for query " + entry.getKey() + ": [");
                for (int i = 0; i < entry.getValue().size()-1; i++) {
                   System.out.print(entry.getValue().get(i) + ", ");
                }
                System.out.println(entry.getValue().get(entry.getValue().size()-1) + "]");
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

}
