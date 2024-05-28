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
        var vectorEntryList = getRawVectorEntrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByKey().reversed())
                .toList();
        int max = getRawVectorEntrySet()
                .stream()
                .max(Map.Entry.<String, Integer>comparingByValue())
                .get()
                .getValue();
        int m = dc.getSize();  // the number of documents

        // calculate tf-idf for each  entry
        for (Map.Entry<String, Integer> stringIntegerEntry : vectorEntryList) {
            String w = stringIntegerEntry.getKey();  // the current word
            int f = stringIntegerEntry.getValue();  // the frequency of the current word
            int df = dc.getDocumentFrequency(w);  // calculating document frequency, the number of documents that contain the current word w
            double tf = 0.5 + (0.5*f) / max;  // term frequency
            double idf = (df == 0 ? 0 : Math.log(((double) m) / df) / Math.log(2));  // inverse document frequency

            normalizedVector.put(w, tf * idf);
        }
    }

    @Override
    public double getNormalizedFrequency(String word) {
        return normalizedVector.get(word);
    }
}
