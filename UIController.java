import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class UIController {
    
    private static UIController instance = new UIController();
    private final int LIST_ITEM_NUMBER = 100;

    private UIController(){

    }

    public void printTable(List<Entry<String, Integer>> map1, List<Entry<String, Integer>> map2, List<Entry<String, Integer>> map3){
    
        Iterator<Entry<String, Integer>> map1_it = map1.iterator();
        Iterator<Entry<String, Integer>> map2_it = map2.iterator();
        Iterator<Entry<String, Integer>> map3_it = map3.iterator();
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
        System.out.println(String.format("\n%6s   %-14s%14s       %-14s%14s       %-19s%19s", " ", "1-Gram", "Frequency", "2-Gram", "Frequency", "3-Gram", "Frequency"));
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
        for(int i=0; i < LIST_ITEM_NUMBER; i++){
            Entry<String, Integer> map1_item = map1_it.next();
            Entry<String, Integer> map2_item = map2_it.next();
            Entry<String, Integer> map3_item = map3_it.next();
            System.out.println(String.format("%3d. |   %-23s %4d   |   %-23s %4d   |   %-33s %4d ", 
                i+1, 
                map1_item.getKey(), 
                map1_item.getValue(),
                map2_item.getKey(),
                map2_item.getValue(),
                map3_item.getKey(),
                map3_item.getValue()
            ));
            
        }

        System.out.println("-----------------------------------------------------------------------------------------------------------------------\n");
    }

    public static UIController getInstance(){
        return instance;
    }
}