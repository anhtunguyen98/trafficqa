package LemurDemo;

import lemurproject.indri.QueryAnnotation;
import lemurproject.indri.QueryEnvironment;
import lemurproject.indri.ScoredExtentResult;

public class RetrievalDemo {

    public static void main(String[] args) {
        String indexPath = "E:\\thien\\Learning\\NLP\\Project\\ConvertToXML\\law indexing";
        String query = "chạy quá tốc độ quy định của xe ô tô";
        try {
            QueryEnvironment env = new QueryEnvironment();
            env.addIndex(indexPath);

            int maxDocs = 10;
            QueryAnnotation results = env.runAnnotatedQuery(query, maxDocs);
            ScoredExtentResult[] scores = results.getResults();
            String[] names = env.documentMetadata(scores, "docno");
            String[] titles = env.documentMetadata(scores, "title");

            for (int i = 0; i < scores.length; i++) {
                System.out.println(names[i] + " " + titles[i] + " " + scores[i].score);
            }
        } catch (Exception localException) {
        }
    }
}
