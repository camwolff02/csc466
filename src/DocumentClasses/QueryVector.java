package DocumentClasses;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class QueryVector extends TextVector {
    private HashMap<String, Double> normalizedVector = new HashMap<>();

    public QueryVector(Stream<String> words) {
        super(words);
    }

    @Override
    public Set<Map.Entry<String, Double>> getNormalizedVectorEntrySet() {
        return normalizedVector.entrySet();
    }

    @Override
    public void normalize(DocumentCollection dc) {
        int max = getHighestRawFrequency();
        int m = dc.getSize();  // the number of documents

        // calculate tf-idf for each  entry
        for (Map.Entry<String, Integer> stringIntegerEntry : getRawVectorEntrySet()) {
            String w = stringIntegerEntry.getKey();  // the current word
            int df = dc.getDocumentFrequency(w);  // calculating document frequency, the number of documents that contain the current word w

            if (df == 0 || max == 0) {
                normalizedVector.put(w, 0.0);
            }
            else {
                int f = stringIntegerEntry.getValue();  // the frequency of the current word
                double tf = 0.5 + (0.5*f) / max;  // term frequency
                double idf = Math.log(m / (double)df) / Math.log(2);  // inverse document frequency

                normalizedVector.put(w, tf * idf);
            }
        }
    }

    @Override
    public double getNormalizedFrequency(String word) {
        return normalizedVector.get(word);
    }
}
