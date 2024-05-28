package DocumentClasses;

import javax.swing.*;
import java.util.Map;

public class CosineDistance implements DocumentDistance {
    @Override
    public double findDistance(TextVector query, TextVector document, DocumentCollection documents) {
        // TODO implement
        var querySeparated = query.getNormalizedVectorEntrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByKey().reversed())
                .toList();

        var documentSeparated = document.getNormalizedVectorEntrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByKey().reversed())
                .toList();

        double dotProduct = 0;
        for (int i = 0; i < querySeparated.size(); i++) {
           dotProduct += querySeparated.get(i).getValue() * documentSeparated.get(i).getValue();
        }
        return dotProduct / (query.getRawVectorEntrySet().size() * document.getRawVectorEntrySet().size());
    }
}
