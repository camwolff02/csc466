package labs;

import DocumentClasses.DocumentCollection;


public class Lab1 {
    public static String pathname = "src/files/documents.txt";

    public static void main(String[] args) {
        // read in documents
        DocumentCollection docs = new DocumentCollection(pathname);


        // ... print the word with the highest single document frequency and the frequency
        // ... print the sum of the distinct number of words in each document over all documents
        // ... print the sum of the frequencies of all non-noise words that are stored

        // ... serialize documents to file
        /*
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(new File("./files/docvector")))) {
            os.writeObject(docs);
        } catch (Exception e) {
            System.out.println(e);
        }*/
    }
}
