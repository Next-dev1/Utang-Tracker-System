import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddList{
    private final ArrayList<Double> oldTotal = new ArrayList<>();
    private final ArrayList<String> realItem = new ArrayList<>();
    private final ArrayList<Double> itemPrice = new ArrayList<>();
    private final ArrayList<String> fullLine = new ArrayList<>();
    private final ArrayList<String> itemList = new ArrayList<>();
    private final ArrayList<Double> priceList = new ArrayList<>();
    private final ArrayList<Integer> quantityList = new ArrayList<>();
    private final ArrayList<Double> totalPriceList = new ArrayList<>();
    private final ArrayList<Double> totalAmountDueList = new ArrayList<>();
    
    public void adding() throws FileNotFoundException, IOException{
        Scanner user = new Scanner(System.in);
        SukiList sukiList = new SukiList();
        
        sukiList.sukiList();
        
        String sukiName;
        String ext = ".txt";
        System.out.println("-------------------------------------------------------------------------------");
        System.out.print("Enter suki name: ");
        sukiName = user.nextLine();
        System.out.println("");
        
        String fileName = sukiName + ext;
        
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        
        String oldDue = "TOTAL AMOUNT DUE: ";
        String line;
        
        while((line = br.readLine()) != null){
            if(line.contains(oldDue)){
                oldTotal.add(Double.valueOf(line.substring(oldDue.length()).trim()));
            }
        }
        
        while(true){
            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?", Pattern.CASE_INSENSITIVE);
            BufferedReader br2 = new BufferedReader(new FileReader("ItemsList.txt"));
            
            String line2;
            boolean finish = false;
            String itemName;

            realItem.clear();
            itemPrice.clear();
            finish = false;

            System.out.print("Enter item name: ");
            itemName = user.nextLine();
            System.out.println(" ");
            
            while ((line2 = br2.readLine()) != null && !finish) {
                if (line2.contains(itemName)) {
                    fullLine.add(line2);
                }

                for (String fileLine : fullLine) {
                    Matcher matcher = pattern.matcher(fileLine);

                    if (matcher.find()) {
                        String priceString = matcher.group();
                        double price = Double.parseDouble(priceString);
                        itemPrice.add(price);

                        String item = fileLine.replace(priceString, "").trim();
                        realItem.add(item);

                        finish = true;
                    }
                }
            }
            System.out.print("Item name: ");
            for (String name : realItem) {
                System.out.println(name);
                itemList.add(name);
            }

            double totalPrice = 0;

            System.out.print("Item price: ");
            for (double price : itemPrice) {
                System.out.println(price + " PHP");
                priceList.add(price);
                totalPrice += price;
            }

            int itemQuantity;
            System.out.println(" ");
            System.out.print("Enter quantity: ");
            itemQuantity = user.nextInt();
            user.nextLine();
            System.out.println(" ");
            quantityList.add(itemQuantity);
            
            totalPrice *= itemQuantity;

            System.out.println("Total price for this item: " + totalPrice + " PHP");
            System.out.println(" ");
            totalPriceList.add(totalPrice);
            
            System.out.println("-------------------------------------------------------------------------------");
            String choice;
            System.out.print("Press the ENTER key to continue listing or type 'DONE' to finish listing");
            choice = user.nextLine();
            System.out.println("-------------------------------------------------------------------------------");
            
            if (choice.equalsIgnoreCase("done")) {
                for (int i = 0; i < itemList.size(); i++) {
                    System.out.println("Item name: " + itemList.get(i));
                    System.out.println("Item price: " + priceList.get(i));
                    System.out.println("Item quantity: " + quantityList.get(i));
                    System.out.println("Total price for this item: " + totalPriceList.get(i));
                    System.out.println(" ");
                }

                double totalAmountDue = 0;

                for (int c = 0; c < totalPriceList.size(); c++) {
                    totalAmountDue += totalPriceList.get(c);
                }
                System.out.println("TOTAL AMOUNT DUE: " + totalAmountDue + " PHP");
                totalAmountDueList.add(totalAmountDue);
                System.out.println("-------------------------------------------------------------------------------");
                
                String confirm;

                System.out.println("-------------------------------------------------------------------------------");
                System.out.print("Are the listed items correct? (YES/NO): ");
                confirm = user.nextLine();
                System.out.println("-------------------------------------------------------------------------------");

                if (confirm.equalsIgnoreCase("no")) {
                    itemList.clear();
                    priceList.clear();
                    quantityList.clear();
                    totalPriceList.clear();
                    totalAmountDueList.clear();
                }
                else if(confirm.equalsIgnoreCase("yes")){
                    CreateList createList = new CreateList();
                    
                    LocalDate dateListed = LocalDate.now();
                    LocalTime timeListed = LocalTime.now();
                    
                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("EEEE, MM/dd/yyyy");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                    
                    String finalTimeListed = timeListed.format(formatter);
                    String finalDateListed = dateListed.format(formatter2);
                    System.out.println("-------------------------------------------------------------------------------");
                    String sellerName;
                    System.out.print("Enter seller name: ");
                    sellerName = user.nextLine();
                    System.out.println(" ");
                    System.out.println("-------------------------------------------------------------------------------");
                    BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true));
                    bw.append(" ");
                    bw.newLine();
                    bw.append("SELLER NAME: " + sellerName);
                    bw.newLine();
                    bw.append("DATE LISTED: " + finalDateListed);
                    bw.newLine();
                    bw.append("TIME LISTED: " + finalTimeListed);
                    bw.newLine();
                    bw.append(" ");
                    bw.newLine();
             
                    for(int i = 0; i < itemList.size(); i++){ //prints the item and its details together sa file na
                    bw.append("Item name: " + itemList.get(i));
                    bw.newLine();
                    bw.append("Item price: " + priceList.get(i));
                    bw.newLine();
                    bw.append("Item quantity: " + quantityList.get(i));
                    bw.newLine();
                    bw.append("Total price for this item: " + totalPriceList.get(i));
                    bw.newLine();
                    }
                    
                    double sum = totalAmountDueList.get(0) + oldTotal.get(0);
                    bw.append(" ");
                    bw.newLine();
                    bw.append("TOTAL AMOUNT DUE: " + sum);
                    bw.newLine();
                    bw.close();
                    System.out.println("-------------------------------------------------------------------------------");
                    System.out.println("                      Debt successfully listed to " + sukiName);
                    System.out.println("===============================================================================");
                    break;
                }
            }
        }    
    }
}