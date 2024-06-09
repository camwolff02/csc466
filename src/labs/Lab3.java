package labs;

import DocumentClasses.CosineDistance;
import DocumentClasses.DocumentCollection;
import DocumentClasses.OkapiDistance;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Lab3 {
    public static final String PATH = "src/files/" ;

    public static DocumentCollection documents;
    public static DocumentCollection queries;

    public static void main(String[] args) {
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(PATH + "docvector"))) {
            // load data from the binary file
            documents = (DocumentCollection) stream.readObject();

            // initialize the queries variable
            queries = new DocumentCollection(PATH + "queries.txt", "query");

            // call normalize() on documents and queries
            documents.normalize(documents);
            queries.normalize(documents);

            // compute the top 20 documents for each query using the TF-IDF algorithm
            // and cosine/okapi similarity
            HashMap<Integer, ArrayList<Integer>> cosineResults = new HashMap<>();
            HashMap<Integer, ArrayList<Integer>> okapiResults = new HashMap<>();
            var cosineDist = new CosineDistance();
            var okapiDist = new OkapiDistance();

            for (var queryEntry : queries.getEntrySet()) {
                cosineResults.put(queryEntry.getKey(), queryEntry.getValue().findClosestDocuments(documents, cosineDist, 20));
                okapiResults.put(queryEntry.getKey(), queryEntry.getValue().findClosestDocuments(documents, okapiDist, 20));
            }

            // Compute human judgement and compare with MAP score
            HashMap<Integer, ArrayList<Integer>> humanJudgement = readHumanJudgement(PATH + "human_judgement.txt");

            System.out.println("Cosine MAP = " + computeMAP(humanJudgement, cosineResults, 20));
            System.out.println("Okapi MAP = " + computeMAP(humanJudgement, okapiResults, 20));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<Integer, ArrayList<Integer>> readHumanJudgement(String filename) throws IOException {
        Scanner in = new Scanner(new FileInputStream(filename));
        HashMap<Integer, ArrayList<Integer>> humanJudgement = new HashMap<>();

        while (in.hasNextLine()) {
            List<Integer> line = Arrays.stream(in.nextLine().split(" "))
                    .map(Integer::parseInt)
                    .toList();

            int queryNumber = line.get(0);
            int documentNumber = line.get(1);
            int relevance = line.get(2);

            if (relevance >= 1 && relevance <= 3) {
                if (humanJudgement.containsKey(queryNumber)) {
                    humanJudgement.get(queryNumber).add(documentNumber);
                }
                else {
                    humanJudgement.put(queryNumber, new ArrayList<>(List.of(documentNumber)));
                }
            }
        }
        return humanJudgement;
    }

    public static double computeMAP(HashMap<Integer, ArrayList<Integer>> human, HashMap<Integer, ArrayList<Integer>> computer, int maxQuery) {
        double MAP = 0;
        for (var queryEntry : computer.entrySet()) {
            var returnedDocuments = queryEntry.getValue();
            if (!human.containsKey(queryEntry.getKey())) {
                continue;
            }
            if (queryEntry.getKey() > maxQuery) {
                return MAP / maxQuery;
            }
            var relevantDocuments = human.get(queryEntry.getKey());

            double AP = 0;
            int correctScanned = 0;
            int currentScanned = 1;

            for (int documentID : returnedDocuments) {
                if (relevantDocuments.contains(documentID)) {
                    correctScanned++;
                    AP += correctScanned / (double)currentScanned;
                }
                currentScanned++;
            }
            MAP += AP / relevantDocuments.size();
        }
        return MAP / computer.size();
    }

}
