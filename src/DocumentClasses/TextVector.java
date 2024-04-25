package DocumentClasses;
import java.io.Serializable;
import java.util.*;

public class TextVector implements Serializable {
    private final HashMap<String, Integer> rawVector;  // mapping of words to their frequencies
    // a word must contain only letters, and must have two or more letters
    // assume anything that is not a letter is a word separator
    // convert all words to lowercase when storing here
    private String maxWord;
    private int maxFrequency;
    private int nonNoiseWords;

    public static  Set<String> noiseWordArray = new HashSet<>(Arrays.asList("a", "about", "above", "all", "along",
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
     * Constructs an empty instance of the TextVector class.
      */
    public TextVector() {
        this.rawVector = new HashMap<>();
    }

    /**
     * Constructs an instance of the TextVector class using a list of strings in a documents
     * @param words a document broken into a list of strings
     */
    public TextVector(String[] words) {
        this.rawVector = new HashMap<>();
        for (var word : words) {
            add(word);
        }
    }

    /***
     * Returns a sparse representation of a text vector.
     * @return a mapping from each word to its frequency
     */
    public Set<Map.Entry<String, Integer>> getRawVectorEntrySet() {
       return rawVector.entrySet();
    }

    /***
     * Adds a word to the rawVector. If the word is not new, frequency is incremented by one
     * @param word word to add to a string
     */
    public void add(String word) {
        if (rawVector.containsKey(word)) {
            rawVector.put(word, rawVector.get(word)+1);
        }
        else {
            rawVector.put(word, 1);
        }

        if (!noiseWordArray.contains(word)) {
           nonNoiseWords++;

           if (rawVector.get(word) > maxFrequency) {
               maxFrequency = rawVector.get(word);
               maxWord = word;
           }
        }
    }

    /***
     * @param word a word that may or may not be in the vector.
     * @return true if the word is in the rawVector, false otherwise
     */
    public boolean contains(String word) {
        return rawVector.containsKey(word);
    }

    /***
     * @param word a word that may or may not be in the vector.
     * @return the frequency of the word
     */
    public int getRawFrequency(String word) {
        if (rawVector.containsKey(word)) {
            return rawVector.get(word);
        }
        return 0;
    }

    /***
     * @return the total number of non-noise words that are stored for the document
     */
    public int getTotalWordCount() {
//        int wordCount = 0;
//        for (var word : rawVector.keySet()) {
//            if (!noiseWordArray.contains(word)) {
//               wordCount += rawVector.get(word);
//            }
//        }
//        return wordCount;
        return nonNoiseWords;
    }

    /***
     * @return the number of distinct words that are stored
     */
    public int getDistinctWordCount() {
//        int distinctCount = 0;
//        for (var freq : rawVector.entrySet()) {
//           distinctCount += freq.getValue();
//        }
//        return distinctCount;
        return rawVector.size();
    }

    /***
     * @return the highest word frequency
     */
    public int getHighestRawFrequency() {
        return maxFrequency;
    }

    /***
     * @return the word with the highest frequency
     */
    public String getMostFrequentWord() {
        return maxWord;
    }
}