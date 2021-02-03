import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {
    public static FileManager instance = new FileManager();
    
    private FileManager(){
    }

    public String readAllLines(String fileName) throws Exception{
        String content = "";
        
        System.out.println("Reading " + fileName);
        content = new String(Files.readAllBytes(Paths.get(fileName))).replaceAll("[\n\r]", " ").trim() + " ";
        return content;
    }

    public String readAllLines(String[] fileNames){
        String content = "";
        int i = 0;
        long startTime = System.currentTimeMillis();
        for(String fileName : fileNames){
            try{
                content += readAllLines(fileName).toLowerCase();
                i += 1;
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
            }
            
        }
        System.out.println("\n" + i + " files has been read in " + (System.currentTimeMillis() - startTime) + " milliseconds.\n-----------------------------------------------------------------------------------------------------------------------");
        return content;
    }

    public static FileManager getInstance(){
        return instance;
    }
}