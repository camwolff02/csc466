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

    }

    @Override
    public double getNormalizedFrequency(String word) {
        return 0;
    }
}
