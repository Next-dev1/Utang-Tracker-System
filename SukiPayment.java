import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SukiPayment{
    private final ArrayList<Double> due = new ArrayList<>();
    private final ArrayList<Double> pay = new ArrayList<>();
    
    public void payment() throws IOException{
    Scanner user = new Scanner(System.in);
    ViewList viewList = new ViewList();
        
    viewList.viewList();
    String fileName = viewList.getFileName();
    File file = new File(fileName);
    
    double payment;
        while(true){
            System.out.print("Enter amount to pay: ");
            payment = user.nextDouble();
            
            user.nextLine();
            pay.add(payment);
            
            String confirm;

            System.out.print("Is the entered amount correct? (YES/NO): ");
            confirm = user.nextLine();
            
            if(confirm.equalsIgnoreCase("no")){
                continue;
            }
            else if(confirm.equalsIgnoreCase("yes")){
                BufferedReader br = new BufferedReader(new FileReader(fileName));
                BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true));
                String keyword = "TOTAL AMOUNT DUE: ";
                String line;
                
                while((line = br.readLine()) != null){
                    if(line.contains(keyword)){
                       due.add(Double.valueOf(line.substring(keyword.length()).trim()));
                    }
                }
                
                double newBalance = due.get(0) - pay.get(0);
                
                if(newBalance == 0){
                    System.out.println("REMAINING BALANCE: " + newBalance + " PHP");
                    br.close();
                    file.delete();
                }
                else{
                    System.out.println("REMAINING BALANCE: " + newBalance + " PHP");
                    br.close();
                    bw.append(" ");
                    bw.newLine();
                    bw.append("TOTAL AMOUNT DUE: " + newBalance);
                    bw.newLine();
                    bw.close();
                }
            }
            break;
        }

    }
}