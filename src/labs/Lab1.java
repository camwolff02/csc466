package labs;

import DocumentClasses.DocumentCollection;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class Lab1 {
    public static String pathname = "src/files/documents.txt";

    public static void main(String[] args) {
        // read in documents
        DocumentCollection docs = new DocumentCollection(pathname, "document");
        int maxFreq = 0;
        int distinctWordCount = 0;
        int nonNoiseWordFreq = 0;
        String maxWord = "";

        for (var docvector : docs.getDocuments()) {
           if (docvector.getHighestRawFrequency() > maxFreq) {
              maxFreq = docvector.getHighestRawFrequency();
              maxWord = docvector.getMostFrequentWord();
           }

           distinctWordCount += docvector.getDistinctWordCount();

           for (var entry : docvector.getRawVectorEntrySet()) {
               if (!docs.isNoiseWord(entry.getKey())) {
                  nonNoiseWordFreq += entry.getValue();
               }
           }

        }
        // 1 ... print the word with the highest single document frequency and the frequency
        System.out.println("Word = " + maxWord);
        System.out.println("Frequency = " + maxFreq);

        // 2 ... print the sum of the distinct number of words in each document over all documents
       System.out.println("Distinct Number of Words = " + distinctWordCount);

        // 3 ... print the sum of the frequencies of all non-noise words that are stored
        System.out.println("Total word count = " + nonNoiseWordFreq);

        // ... serialize documents to file
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("src/files/docvector"))) {
            os.writeObject(docs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
