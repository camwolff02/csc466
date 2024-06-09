package DocumentClasses;

public class OkapiDistance implements DocumentDistance {
    private double k1 = 1.2;
    private double b = 0.75;
    private int k2 = 100;

    public OkapiDistance() {}

    public OkapiDistance(double k1, double b, int k2) {
        this.k1 = k1;
        this.b = b;
        this.k2 = k2;
    }

    @Override
    public double findDistance(TextVector query, TextVector document, DocumentCollection documents) {
        final int N = documents.getSize();  // total number of documents in collection
        double avdl = documents.getAverageDocumentLength();  // average document length (number of terms)

        double okapi = 0;

        // okapi is a summation over all similar words
        for (var queryEntry : query.getRawVectorEntrySet()) {
            String t_i = queryEntry.getKey();
            if (document.contains(t_i)) {
                int df_i = documents.getDocumentFrequency(t_i);  // number of documents that contain the term t_i
                int f_i_j = document.getRawFrequency(t_i);  // number of times the term t_i appears in a document d_j
                int f_i_q = queryEntry.getValue();  // number of times the term t_i appears in a query q
                int dl_j = document.getTotalWordCount();  // length of document d_j

                okapi += Math.log((N - df_i+ 0.5) / (df_i + 0.5))
                        * (((k1 + 1.0) * f_i_j)) / (k1*(1.0-b+b*(dl_j/avdl)) + f_i_j)
                        * (((k2 + 1.0) * f_i_q) / (k2 + f_i_q));
            }
        }
        return okapi;
    }
}
