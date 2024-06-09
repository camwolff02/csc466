package DocumentClasses;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.Entry.comparingByValue;


public abstract class TextVector implements Serializable {
    private final HashMap<String, Integer> rawVector;  // mapping of words to their frequencies
    // a word must contain only letters, and must have two or more letters
    // assume anything that is not a letter is a word separator
    // convert all words to lowercase when storing here
    private String maxWord;
    private int maxFrequency;
    private int wordCount;

    /**
     * @return the normalized frequency for each word
     * Override in child class
     */
    public abstract Set<Map.Entry<String, Double>> getNormalizedVectorEntrySet();

    /**
     * @param dc will normalize the frequency of each word in dc using the TF-IDF formula
     * Override in child class
     */
    public abstract void normalize(DocumentCollection dc);

    /**
     * @param word to normalize frequency of
     * @return the normalized frequency of word
     * Override in child class
     */
    public abstract double getNormalizedFrequency(String word);

    /**
     * Calculates and returns the magnitude of this vector
     * @return the square root of the sum of the squares of the normalized frequencies
     */
    public double getL2Norm() {
        double l2Sum = 0;
        for (var word : rawVector.keySet()) {
           l2Sum += Math.pow(getNormalizedFrequency(word), 2);
        }
        return Math.sqrt(l2Sum);
    }

    /**
      * @param documents set of documents to compare to this vector
      * @param distanceAlg distance metric to use when comparing closeness of 2 vectors
     * @return the 20 closest documents to this vector
     */
    public ArrayList<Integer> findClosestDocuments(DocumentCollection documents, DocumentDistance distanceAlg, int closestN) {
        Map<Integer, Double> distances = new HashMap<>();

        for (var entry : documents.getEntrySet()) {
            if (entry.getValue().getTotalWordCount() == 0 || getTotalWordCount() == 0) {
                distances.put(entry.getKey(), 0.0);
            }
            else {
                distances.put(entry.getKey(), distanceAlg.findDistance(this, entry.getValue(), documents));
            }
        }

        return distances.entrySet().stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .limit(closestN)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
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

    public TextVector(Stream<String> words) {
       this.rawVector = new HashMap<>();
       words.forEach(this::add);
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

        if (rawVector.get(word) > maxFrequency) {
           maxFrequency = rawVector.get(word);
           maxWord = word;
        }

        wordCount++;
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
     * @return the total number of words that are stored for the document
     */
    public int getTotalWordCount() {
        return wordCount;
    }

    /***
     * @return the number of distinct words that are stored
     */
    public int getDistinctWordCount() {
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