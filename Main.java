import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;

public class Main {

    private static FileManager fm = FileManager.getInstance();
    private static NGramAnalyzer nga = NGramAnalyzer.getInstance();
    private static UIController uic = UIController.getInstance();

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();
        String allFiles = fm.readAllLines(new String[] { 
            "Novel-Samples/BÝLÝM ÝÞ BAÞINDA.txt",
            "Novel-Samples/BOZKIRDA.txt", 
            "Novel-Samples/DEÐÝÞÝM.txt", 
            "Novel-Samples/DENEMELER.txt",
            "Novel-Samples/UNUTULMUÞ DÝYARLAR.txt"});
        if(allFiles.length() > 0){
            System.out.println("Removing punctuations and multiple spaces...\n-----------------------------------------------------------------------------------------------------------------------");
            allFiles = allFiles.replaceAll("['\u0307’”“—]", "").replaceAll("[(),.?!:\"»«\t*;]", " ").replaceAll("-", " ").replaceAll("( )+", " ");    
            List<Entry<String, Integer>> one_gram = nga.analyze(1, allFiles);
            List<Entry<String, Integer>> two_gram = nga.analyze(2, allFiles);
            List<Entry<String, Integer>> three_gram = nga.analyze(3, allFiles);
            uic.printTable(one_gram, two_gram, three_gram);
            System.out.println("Execution completed in " + (System.currentTimeMillis() - startTime) + " milliseconds.\n");
        }
        else{
            System.out.println("\nThere is not any word to analyze!\nPLEASE PUT TXT FILES IN Novel-Samples FOLDER!!!!\n");
        }
        System.out.print("Press enter to continue . . .");
        new Scanner(System.in).nextLine();
    }
}