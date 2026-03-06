import java.util.*;

public class StepProblem4 {

    // Create n-grams
    public static List<String> createNGrams(String text, int n) {
        String[] words = text.toLowerCase().split(" ");
        List<String> ngrams = new ArrayList<>();

        for (int i = 0; i <= words.length - n; i++) {
            String gram = "";
            for (int j = 0; j < n; j++) {
                gram = gram + words[i + j] + " ";
            }
            ngrams.add(gram.trim());
        }

        return ngrams;
    }

    public static void main(String[] args) {

        int n = 3; // 3-word n-grams

        String doc1 = "java is a programming language used for software development";
        String doc2 = "java is a programming language used for web development";

        List<String> grams1 = createNGrams(doc1, n);
        List<String> grams2 = createNGrams(doc2, n);

        // store n-grams of doc1 in hash map
        HashMap<String, Integer> map = new HashMap<>();

        for (String g : grams1) {
            map.put(g, 1);
        }

        int matchCount = 0;

        // check matches
        for (String g : grams2) {
            if (map.containsKey(g)) {
                matchCount++;
            }
        }

        // similarity percentage
        double similarity = (double) matchCount / grams1.size() * 100;

        System.out.println("Matching n-grams: " + matchCount);
        System.out.println("Similarity: " + similarity + "%");
    }
}