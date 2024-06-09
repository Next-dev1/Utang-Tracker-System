import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ViewList{
    private ArrayList<String> list = new ArrayList<>();
    private String sukiName;
    private String fileName;
    
    public void setSukiName(String sukiName){
        this.sukiName = sukiName;
    }
    public String getSukiName(){
        return sukiName;
    }
    public void setFileName(String fileName){
        this.fileName = sukiName + ".txt";
    }
    public String getFileName(){
        return fileName;
    }
    public void viewList() throws IOException{
        SukiList sukiList = new SukiList();
        
        sukiList.sukiList();
        
        Scanner user = new Scanner(System.in);
        
        System.out.println("-------------------------------------------------------------------------------");
        System.out.print("Enter suki name: ");
        sukiName = user.nextLine();
        System.out.println("-------------------------------------------------------------------------------");

        String fileName = sukiName + ".txt";
        
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        
        String line;
        
        while((line = br.readLine()) != null){
            list.add(line);
        }
        
        for(String debt : list){
            System.out.println(debt);
        }
    }
}