import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public class NGramAnalyzer {
    private static NGramAnalyzer instance = new NGramAnalyzer();

    private NGramAnalyzer(){
    }

    public List<Entry<String, Integer>> analyze(int n, String str){
        System.out.print("Generating " + n + "-gram .... ");
        long startTime = System.currentTimeMillis();
        String[] words = str.split(" ");
        HashMap<String, Integer> n_grams = new HashMap<String, Integer>();
        
        for(int i = n-1; i < words.length ; i++){
            String n_gram = "";
            for(int k = i; k > i-n ; k--){
                n_gram = words[k] + " " + n_gram;
            }
        
            if(!n_grams.containsKey(n_gram)){
                n_grams.put(n_gram, 1);
            }
            else{
                int val = n_grams.get(n_gram);
                n_grams.put(n_gram, val + 1); 
            }   
        }
        System.out.print("Sorting .... ");
        List<Entry<String, Integer>> n_gram_list = new LinkedList<Entry<String, Integer>>(n_grams.entrySet());  
        Collections.sort(n_gram_list, new Comparator<Entry<String, Integer>>(){

			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
            
        });
        System.out.println("Completed in " + (System.currentTimeMillis() - startTime) + " milliseconds.");
        return n_gram_list;
    }

    public static NGramAnalyzer getInstance(){
        return instance; 
    }
}

