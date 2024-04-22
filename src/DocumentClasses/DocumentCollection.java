package DocumentClasses;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocumentCollection implements Serializable {
    private HashMap<Integer, TextVector> documents;  // mapping of document indexes to their content text vector
    private Set<String> words;

    /***
     * Constructor for the Document collection class. Takes a file as file to parse, and uses file to populate documents variable
     * @param filename of document to parse
     */
    public DocumentCollection(String filename) {

        try {
            // Step 1: Read in the document
            Path filePath = Path.of(filename);
            String docs = Files.readString(filePath);

            // Step 2: Extract the data (the text after .W)
            Pattern pattern = Pattern.compile("(?s)\\.W(.*?)\\.I");
            Matcher matcher = pattern.matcher(docs);

            int idx = 0;

            while (matcher.find()) {
                // Step 3: At this point we have individual documents. Parse words.
                String str = matcher.group();
                String[] words = str.substring(2,str.length()-2)
                                    .trim()
                                    .toLowerCase()
                                    .split("[^a-zA-Z]+");

                documents.put(idx++, new TextVector(words));
            }
        }
        catch (NoSuchFileException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
           e.printStackTrace();
        }
    }

    /***
     *
     * @param id
     * @return the TextVector for the document with the ID that is given
     */
    public TextVector getDocumentById(int id) {
       return documents.get(id);
    }

    /***
     * Uses method getTotalWordCount on each document to calculate the number of noise words in each document.
     * Add up the numbers and divide by the total number of documents
     * @return the average length of a document not counting noise words.
     */
    public double getAverageDocumentLength() {
        int wordCount = 0;
        for (var document : documents.values()) {
            wordCount += document.getTotalWordCount();
        }
        return (double) wordCount / documents.size();
    }

    /***
     * @return the number of documents
     */
    public int getSize() {
        return documents.size();
    }

    /***
     * @return a collection of text vector documents
     */
    public Collection<TextVector> getDocuments() {
        return documents.values();
    }

    /***
     * @return returns a mapping of document id to text vector
     */
    public Set<Map.Entry<Integer, TextVector>> getEntrySet() {
       return documents.entrySet();
    }

    /***
     * @param word a word to get the frequency of
     * @return the number of documents that contain the input word
     */
    public int getDocumentFrequency(String word) {
        int df = 0;
        for (var document : documents.values()) {
            if (document.contains(word)) {
                df++;
            }
        }
        return df;
    }

    /***
     * @param word a word that may or may not be in the document
     * @return true if word is a noise word, false otherwise
     */
    private boolean isNoiseWord(String word) {
       return TextVector.noiseWordArray.contains(word);
    }
}

