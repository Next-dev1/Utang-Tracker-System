
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SukiList{
    public void sukiList() throws FileNotFoundException, IOException{
        
        Scanner user = new Scanner(System.in);
        
        BufferedReader br = new BufferedReader(new FileReader("SukiNames.txt"));
        
        String keyword = "SUKI NAME:";
        String line;
        int lineNumber = 0;
        ArrayList<String> names = new ArrayList<>();
        
        
        while((line = br.readLine()) != null){
            lineNumber++;
            
            if(line.contains(keyword)){
                String nameSuki = line.substring(keyword.length());
                names.add(nameSuki.trim());
            }
        }
        ArrayList<String[]> splitList = new ArrayList<>();
                
            for (String sukiName : names){
                String[] splitArray = sukiName.split("\n");
                splitList.add(splitArray);
                    
            }
            int index = 1;
            
            for (String[] finalNames : splitList){
                for(String name : finalNames){
                    System.out.println(index + ".) " + name.trim());
                    index++;
                }
            }
        
    }
}