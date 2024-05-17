package labs;

import DocumentClasses.DocumentCollection;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Lab2 {
    public static DocumentCollection documents;
    public static DocumentCollection queries;

    public static void main(String[] args) {
        // Instructions:
        // load data from the binary file
        // initialize the queries variable
        // call normalize() on documents and queries
        // print the 20 most relevant documents for each query

        //...
        // Example code that reads the document vectors data
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(new File("./files/docvector")))) {
            var documentVectors = (DocumentCollection)stream.readObject();
        } catch (Exception e) {
           e.printStackTrace();
        }


    }

}
