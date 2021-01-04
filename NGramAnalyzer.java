import java.util.Comparator;
import java.util.TreeMap;

public class NGramAnalyzer {
    private static NGramAnalyzer instance = new NGramAnalyzer();

    private NGramAnalyzer(){
    }

    public TreeMap<String, Integer> analyze(int n, String str){
        System.out.print("Generating " + n + "-gram .... ");
        long startTime = System.currentTimeMillis();
        String[] words = str.split(" ");
        TreeMap<String, Integer> n_grams = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);

        for(int i = n-1; i < words.length ; i++){
            String n_gram = "";
            for(int k = i; k > i-n ; k--){
                n_gram = words[k] + " " + n_gram;
            }
        
            if(!n_grams.containsKey(n_gram)){
                n_grams.put(n_gram, 0);
            }
            int val = n_grams.get(n_gram);
            n_grams.put(n_gram, val + 1); 
        }
        System.out.print("Sorting .... ");
        n_grams = sortByValues(n_grams);
        System.out.println("Completed in " + (System.currentTimeMillis() - startTime) + " milliseconds.");
        return n_grams;
    }

    public TreeMap<String, Integer> sortByValues(TreeMap<String, Integer> treeMap) {
        Comparator<String> valueComparator = new Comparator<String>() {
            public int compare(String k1, String k2) {
            int compare = treeMap.get(k2).compareTo(treeMap.get(k1));
            if (compare == 0) 
                return 1;
            else 
                return compare;
            }
        };
 
        TreeMap<String, Integer> sortedByValues = new TreeMap<String, Integer>(valueComparator);
        sortedByValues.putAll(treeMap);
        return sortedByValues;
    }

    public static NGramAnalyzer getInstance(){
        return instance; 
    }
}