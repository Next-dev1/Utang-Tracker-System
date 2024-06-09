
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

public class CreateList{
    //instance variables...???
    private String sukiName;
    private String sellerName;
    private String itemName;
    private double itemPrice;
    private int itemQuantity;
    
    private final ArrayList<String> itemList = new ArrayList<>();
    private final ArrayList<Double> priceList = new ArrayList<>();
    private final ArrayList<Integer> quantityList = new ArrayList<>();
    private final ArrayList<Double> totalPriceList = new ArrayList<>();
    private final ArrayList<Double> totalAmountDueList = new ArrayList<>();
    
    //ayan, puro mga setter and getters lang kasi mas madali silang tawagin this way 
    public void setSukiName(String suName){
        suName = sukiName;
    }
    public String getSukiName(){
        return sukiName;
    }
    public void setSellerName(String seName){
        seName = sellerName;
    }
    public String getSellerName(){
        return sellerName;
    }
    public void setItemName(String nameItem){
        nameItem = itemName;
    }
    public String getItemName(){
        return itemName;
    }
    public void setItemPrice(double priceItem){
        priceItem = itemPrice;
    }
    public double getItemPrice(){
        return itemPrice;
    }
    public void setItemQuantity(int quantityItem){
        quantityItem = itemQuantity;
    }
    public int getItemQuantity(){
        return itemQuantity;
    }
    public void enterDetails(){
        Scanner user = new Scanner(System.in);
        
        System.out.print("Enter suki name: ");
        sukiName = user.nextLine();
        System.out.print("Enter seller name: ");
        sellerName = user.nextLine();    
    }
    //itong method na to is para mabasa yung item na ililista and compare sa file
    public void enterItem() throws FileNotFoundException, IOException{
        while(true){
        Scanner user = new Scanner(System.in);
        
        ArrayList<String> realItem = new ArrayList<>(); //arraylist to store item name ONLY
        ArrayList<Double> itemPrice = new ArrayList<>(); //arraylist to store item price ONLY
        ArrayList<String> fullLine = new ArrayList<>(); //arraylist to store entire line (contains name & price)
        //ang atake kasi nito is kukunin muna natin yung buong line na may price and item name
        //tapos tsaka lang natin sila paghihiwalayin para tig isa silang mag didisplay
        //dito naman pattern siya na may regex para mahanap kung may double bang nakasulat sa file
        //meron din siyang CASE_INSENSITIVE para kahit anong type mo sa item name, imamatch niya parin
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?", Pattern.CASE_INSENSITIVE);
        //dito natin i-sstore yung line na need natin sa file na babasahin natin bago natin ibato sa arraylist
        String line;
        boolean finish = false; //ito parang lightswitch lang to kung itutuloy pa or tatapusin na ba yung loop
        //so pag naka false yan ibigsabihin tutuloy parin yung loop dito sa baba
        BufferedReader br = new BufferedReader (new FileReader("ItemsList.txt"));

            realItem.clear();
            itemPrice.clear();
            finish = false;
            
            System.out.print("Enter item name: ");
            itemName = user.nextLine().toLowerCase(); //para yung entered value ng user matic lowercase
            System.out.println(" ");

            //ang ginagawa naman nito is kung ano raw yung mababasa sa file
            //yun daw yung value ng line tapos
            //nasa loop siya para tuloy tuloy lang pagbabasa niya sa file
            //so hanggat hindi pa raw 'null' or wala nang mababasa and hindi pa raw true yung finish
            //magtutuloy tuloy lang yung loop and pagbabasa sa file
            while((line = br.readLine()) != null && !finish){
                //kung yung inenter ni user na name ng item is nabasa raw sa line
                if(line.contains(getItemName())){
                    fullLine.add(line); //ang gagawin niya is ibabato niya yung line na yun sa arraylist
                    //dun niya sa arraylist na buong line ang kinukuha ibabato
                    //kasi dun muna dapat siya bago natin himayin into price and item name lang      
                }

                //so ngayon may for loop na siya
                //ibigsabihin nito i-aassign yung laman ng fullLine na arraylist
                //yung mga value niya sa string na fileLine
                for(String fileLine : fullLine){
                    //titingnan nitong matcher kung yung makukuha niyang values
                    //sa fileLine is may nag mamatch dun sa pattern na sinet natin kanina
                    //so parang cinocompare niya if mahahanap niya yung conditions ng pattern
                    //dun sa binabasa niyang value sa fileLine
                    Matcher matcher = pattern.matcher(fileLine);

                    //tong find naman ang pagkakaintindi ko is hahanapin niya 
                    //if meron ngang nag match dun sa cinompare niya sa pattern
                    if(matcher.find()){
                        //tong group naman, kinukuha niya yung substring nung
                        //value na nag match dun sa pattern
                        //eh diba kanina naghahanap nga tayo ng double?
                        //edi in short, kukunin niya yung double lang 
                        String priceString = matcher.group();

                        //since naka string talaga palagi pag from file to program
                        //need nating iconvert yung string na price into double kasi need for computation
                        //so dito nangyayare yung conversion ng from string to double
                        double price = Double.parseDouble(priceString);
                        //since naconvert na natin siya, ibabato na natin siya sa double arraylist natin
                        //kung san natin itatabi yung mga price
                        itemPrice.add(price);
                        //if kanina yung presyo kinukuha, dito is yung item name naman
                        //ang ginagawa niya lang is yung value raw ng fileLine
                        //ireplace daw yung double, gagawin nalang daw niyang blank para mawala
                        //pag nawala na yung presyo dun sa linya, i-ttrim na natin yung line
                        //and since white space na yung pumalit sa double, i-ttrim na natin siya 
                        //para walang space sa beginning niya
                        String item = fileLine.replace(priceString, "").trim();
                        //since nakuha na natin yung name lang ng item, ibabato na natin siya
                        //dun sa arraylist natin na ang iniistore lang is yung name ng item
                        realItem.add(item);

                        //ito na yung magpapatapos sa while loop
                        finish = true;
                    }
                }
            }

            //dito naman ididisplay niya na yung mga nakuha natin kanina from text file
            System.out.print("Item name: ");
            for(String name : realItem){
                System.out.println(name);
                itemList.add(name);
            }
            //ito need kasi natin makuha yung total price so i-seset muna natin siya to zero
            double totalPrice = 0;

            System.out.print("Item price: ");
            for(double price : itemPrice){
                System.out.println(price + " PHP");
                priceList.add(price);
                //then dito ang ginagawa niya is ina-add niya sa sarili niya yung price
                totalPrice += price;
            }
            //kuha quantity, ilang piraso ba nung item na yun yung kukunin?
            System.out.println(" ");
            System.out.print("Enter quantity: ");
            itemQuantity = user.nextInt();
            user.nextLine();
            System.out.println(" ");
            quantityList.add(itemQuantity);

            //yung total price is i-mumultiply mo na sa bilang ng piraso ng item
            totalPrice *= itemQuantity;

            //ayan display mo na yung total price
            System.out.println("Total price for this item: " + totalPrice + " PHP");
            totalPriceList.add(totalPrice);
            System.out.println("-------------------------------------------------------------------------------");
            
            String choice;
            System.out.print("Press the ENTER key to continue listing or type 'DONE' to finish listing: ");
            choice = user.nextLine();
            System.out.println("-------------------------------------------------------------------------------");
            
            if(choice.equalsIgnoreCase("done")){
                for(int i = 0; i < itemList.size(); i++){ //prints the item and its details together
                    System.out.println("Item name: " + itemList.get(i));
                    System.out.println("Item price: " + priceList.get(i));
                    System.out.println("Item quantity: " + quantityList.get(i));
                    System.out.println("Total price for this item: " + totalPriceList.get(i));
                    System.out.println(" ");
                }
                
                double totalAmountDue = 0;
                
                for(int c = 0; c < totalPriceList.size(); c++){ //adds every price stored in the totalPriceList ArrayList
                    totalAmountDue += totalPriceList.get(c);
                }
                System.out.println("TOTAL AMOUNT DUE: " + totalAmountDue + " PHP");
                totalAmountDueList.add(totalAmountDue);
 
                String confirm;
                    
                System.out.println("-------------------------------------------------------------------------------");
                System.out.print("Are the listed items correct? (YES/NO): ");
                confirm = user.nextLine();
                System.out.println("-------------------------------------------------------------------------------");
                    
                if(confirm.equalsIgnoreCase("no")){
                    itemList.clear();
                    priceList.clear();
                    quantityList.clear();
                    totalPriceList.clear();
                    totalAmountDueList.clear();
                }
                else if(confirm.equalsIgnoreCase("yes")){
                    BufferedWriter bw = new BufferedWriter(new FileWriter("UtangList.txt", true));
                    
                    LocalDate dateListed = LocalDate.now();
                    LocalTime timeListed = LocalTime.now();
                    
                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("EEEE, MM/dd/yyyy");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                    
                    String finalTimeListed = timeListed.format(formatter);
                    String finalDateListed = dateListed.format(formatter2);
                    
                    enterDetails();
                    bw.append(" ");
                    bw.newLine();
                    bw.append(getSukiName() + "1");
                    bw.newLine();
                    bw.append("SUKI NAME: " + getSukiName());
                    bw.newLine();
                    bw.append("SELLER NAME: " + getSellerName());
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

                    bw.newLine();
                    bw.append(getSukiName() + "2");
                    bw.newLine();
                    bw.append("TOTAL AMOUNT DUE: " + totalAmountDueList.get(0));
                    bw.newLine();
                    
                    bw.append(getSukiName() + "3");
                    bw.newLine();
                    bw.append(" ");
                    bw.close();
                    System.out.println("-------------------------------------------------------------------------------");
                    System.out.println("                           Debt successfully listed!");
                    System.out.println("===============================================================================");
                    break;
                    }
            }
        }
    }
}