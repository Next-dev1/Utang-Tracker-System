import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ViewList{
    private String sukiName;
    
    public void setSukiName(String sukiName){
        this.sukiName = sukiName;
    }
    public String getSukiName(){
        return sukiName;
    }
    public void viewList() throws IOException{
        SukiList sukiList = new SukiList();

        sukiList.sukiList();
        
        Scanner user = new Scanner(System.in);
        
        System.out.println("-------------------------------------------------------------------------------");
        System.out.print("Enter suki name: ");
        sukiName = user.nextLine();
        System.out.println("-------------------------------------------------------------------------------");
        
        String divider1 = sukiName + "1";
        String divider2 = sukiName + "2";
        String divider3 = sukiName + "3";

        BufferedReader br = new BufferedReader (new FileReader("UtangList.txt"));
        String line;
        boolean isPrinting = false;
        ArrayList<String> debtList = new ArrayList<>();
        
        while((line = br.readLine()) != null){ 
            if(line.contains(divider2)){
                continue;
            }
            if(line.contains(divider1)){
                isPrinting = true;
                continue;
            }
            if(line.contains(divider3)){
                isPrinting = false;
                break;
            }
            if(isPrinting){
                debtList.add(line);
            }
        }
        for(String listDebt : debtList){
            System.out.println(listDebt);   
        }
        System.out.println("-------------------------------------------------------------------------------");
        
    }
}