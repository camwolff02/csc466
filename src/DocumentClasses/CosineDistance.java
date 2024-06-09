package DocumentClasses;

import javax.swing.*;
import java.util.Map;

public class CosineDistance implements DocumentDistance {
    @Override
    public double findDistance(TextVector query, TextVector document, DocumentCollection documents) {
        double dotProduct = 0;

        for (var queryEntry : query.getNormalizedVectorEntrySet()) {
            String queryWord = queryEntry.getKey();
            Double queryFreq = queryEntry.getValue();

            if (document.contains(queryWord)) {
                Double  documentFreq = document.getNormalizedFrequency(queryWord);
                dotProduct += queryFreq * documentFreq;
            }
        }

        return dotProduct / (query.getL2Norm() * document.getL2Norm());
    }
}
