import java.util.Scanner;
import java.util.TreeMap;

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
            "Novel-Samples/UNUTULMUÞ DÝYARLAR.txt", });
        if(allFiles.length() > 0){
            System.out.println("Removing punctuations and extra spaces...\n-----------------------------------------------------------------------------------------------------------------------");
            allFiles = allFiles.replaceAll("\\p{Punct}", "").replaceAll("( )+", " ");    
            TreeMap<String, Integer> one_gram = nga.analyze(1, allFiles);
            TreeMap<String, Integer> two_gram = nga.analyze(2, allFiles);
            TreeMap<String, Integer> three_gram = nga.analyze(3, allFiles);
            uic.printTable(one_gram, two_gram, three_gram);
            System.out.println("Execution completed in " + (System.currentTimeMillis() - startTime) + " milliseconds.\n");
        }
        else{
            System.out.println("\nThere is not any word to analyze!\n");
        }
        System.out.print("Press enter to continue . . .");
        new Scanner(System.in).nextLine();
    }
}