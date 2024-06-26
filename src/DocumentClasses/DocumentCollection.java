package DocumentClasses;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class DocumentCollection implements Serializable {
    private HashMap<Integer, TextVector> documents;  // mapping of document indexes to their content text vector
    
    public static final Set<String> noiseWordArray = new HashSet<>(Arrays.asList("a", "about", "above", "all", "along",
            "also", "although", "am", "an", "and", "any", "are", "aren't", "as", "at",
            "be", "because", "been", "but", "by", "can", "cannot", "could", "couldn't",
            "did", "didn't", "do", "does", "doesn't", "e.g.", "either", "etc", "etc.",
            "even", "ever", "enough", "for", "from", "further", "get", "gets", "got", "had", "have",
            "hardly", "has", "hasn't", "having", "he", "hence", "her", "here",
            "hereby", "herein", "hereof", "hereon", "hereto", "herewith", "him",
            "his", "how", "however", "i", "i.e.", "if", "in", "into", "it", "it's", "its",
            "me", "more", "most", "mr", "my", "near", "nor", "now", "no", "not", "or", "on", "of", "onto",
            "other", "our", "out", "over", "really", "said", "same", "she",
            "should", "shouldn't", "since", "so", "some", "such",
            "than", "that", "the", "their", "them", "then", "there", "thereby",
            "therefore", "therefrom", "therein", "thereof", "thereon", "thereto",
            "therewith", "these", "they", "this", "those", "through", "thus", "to",
            "too", "under", "until", "unto", "upon", "us", "very", "was", "wasn't",
            "we", "were", "what", "when", "where", "whereby", "wherein", "whether",
            "which", "while", "who", "whom", "whose", "why", "with", "without",
            "would", "you", "your", "yours", "yes"));

    /***
     * Constructor for the Document collection class. Takes a file as file to parse, and uses file to populate documents variable
     * @param type if type is "document", adds DocumentVector objects to Hashmap. Otherwise, adds QueryVector objects to hash map.
     * @param filename of document to parse
     */
    public DocumentCollection(String filename, String type) {
        try {
            documents = new HashMap<>();

            // Step 1: Read in the document
            Path filePath = Path.of(filename);
            String docs = Files.readString(filePath);

            // Step 2: Extract the data (the text after .W)
            Pattern pattern = Pattern.compile("(?s)\\.W(.+?)(\\.I|$)");
            Matcher matcher = pattern.matcher(docs);

            int idx = 0;

            while (matcher.find()) {
                // Step 3: At this point we have individual documents. Parse words.
                String str = matcher.group();
                String[] words = str.substring(2,str.length()-2)
                                    .trim()
                                    .toLowerCase()
                                    .split("[^a-zA-Z]+");

                // don't add noise words
                // don't add words of length < 2
                Stream<String> wordStream = Arrays.stream(words).filter(this::isNotNoiseWord)
                                                                .filter((String word) -> word.length() >= 2);

                if (type.equalsIgnoreCase("document")) {
                    documents.put(++idx, new DocumentVector(wordStream));
                }
                else {
                    documents.put(++idx, new QueryVector(wordStream));
                }
            }
        }
        catch (IOException e) {
           e.printStackTrace();
        }
    }

    /***
     * @param id document id
     * @return the TextVector for the document with the ID that is given
     */
    public TextVector getDocumentById(int id) {
       return documents.get(id);
    }

    /**
     * Calls the normalize(dc) method of each document on this collection
      * @param dc document colletion to normalize relative to
     */
    public void normalize(DocumentCollection dc) {
        for (TextVector document : getDocuments()) {
            document.normalize(dc);
        }
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
    public boolean isNoiseWord(String word) {
       return noiseWordArray.contains(word);
    }
    public boolean isNotNoiseWord(String word) { return !isNoiseWord(word); }
}

