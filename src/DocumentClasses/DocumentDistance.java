package DocumentClasses;

public interface DocumentDistance {
    /**
      * @param query
     * @param document
     * @param documents
     * @return the distance between the query and the document
     */
    double findDistance(TextVector query, TextVector document, DocumentCollection documents);
}
