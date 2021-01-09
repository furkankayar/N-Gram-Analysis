import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class FileManager {
    public static FileManager instance = new FileManager();
    
    private FileManager(){
    }

    public String readAllLines(String fileName) throws Exception{
        String content = "";
        
        System.out.println("Reading " + fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        String line = "";
        while((line = br.readLine()) != null){
            content += line + " ";
        }
        br.close();
        return content;
    }

    public String readAllLines(String[] fileNames){
        String content = "";
        int i = 0;
        long startTime = System.currentTimeMillis();
        for(String fileName : fileNames){
            try{
                content += readAllLines(fileName).toLowerCase(new Locale("tr-TR"));
                i += 1;
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
            }
            
        }
        System.out.println("\n" + i + " files has been read in " + (System.currentTimeMillis() - startTime) + " milliseconds.\n-----------------------------------------------------------------------------------------------------------------------");
        return content;
    }


    public void writeString(String str, String fileName){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(str);
            writer.close();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }


    public static FileManager getInstance(){
        return instance;
    }
}