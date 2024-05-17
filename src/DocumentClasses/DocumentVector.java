package DocumentClasses;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class DocumentVector extends TextVector {
    public DocumentVector(Stream<String> words) {
       super(words);
    }

    private HashMap<String, Double> normalizedVector = new HashMap<>();

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
