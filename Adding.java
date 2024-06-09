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

public class Adding {
    private final ArrayList<String> entireList = new ArrayList<>();
    private final ArrayList<String> oldList = new ArrayList<>();
    private final ArrayList<Double> oldDueList = new ArrayList<>();
    private final ArrayList<String> realItem = new ArrayList<>();
    private final ArrayList<Double> itemPrice = new ArrayList<>();
    private final ArrayList<String> fullLine = new ArrayList<>();
    private final ArrayList<String> itemList = new ArrayList<>();
    private final ArrayList<Double> priceList = new ArrayList<>();
    private final ArrayList<Integer> quantityList = new ArrayList<>();
    private final ArrayList<Double> totalPriceList = new ArrayList<>();
    private final ArrayList<Double> totalAmountDueList = new ArrayList<>();
    
    public void addToList() throws FileNotFoundException, IOException {
        Scanner user = new Scanner(System.in);
        BufferedReader br = new BufferedReader(new FileReader("UtangList.txt"));
        SukiList sukiList = new SukiList();

        sukiList.sukiList();

        String sukiName;

        System.out.print("Enter suki name: ");
        sukiName = user.nextLine();
        System.out.println("");

        String divider1 = sukiName + "1";
        String divider2 = sukiName + "2";
        String divider3 = sukiName + "3";

        String oldDue = "TOTAL AMOUNT DUE: ";
        String line;

        boolean getOldList = false;

        while ((line = br.readLine()) != null) {
            if (line.contains(divider1)) {
                getOldList = true;
                continue;
            }
            if (getOldList) {
                if (line.contains(divider2)) {
                    break;
                }
                oldList.add(line);
            }
            if (line.contains(oldDue)) {
                oldDueList.add(Double.valueOf(line.substring(oldDue.length()).trim()));
            }
        }

        boolean getAll = false;

        while ((line = br.readLine()) != null) {
            if (line.contains(divider1)) {
                getAll = true;
                continue;
            }
            if (line.contains(divider3)) {
                getAll = false;
                continue;
            }
            if (getAll) {
                continue;
            }
            entireList.add(line);
        }

        while (true) {
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

            String choice;
            System.out.print("Press the ENTER key to continue listing or type 'DONE' to finish listing");
            choice = user.nextLine();

            if (choice.equalsIgnoreCase("done")) {
                for (int i = 0; i < itemList.size(); i++) {
                    System.out.println(" ");
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

                String confirm;

                System.out.println(" ");
                System.out.print("Are the listed items correct? (YES/NO): ");
                confirm = user.nextLine();
                System.out.println(" ");

                if (confirm.equalsIgnoreCase("no")) {
                    itemList.clear();
                    priceList.clear();
                    quantityList.clear();
                    totalPriceList.clear();
                    totalAmountDueList.clear();
                } else if (confirm.equalsIgnoreCase("yes")) {
                    BufferedWriter bw = new BufferedWriter(new FileWriter("UtangList.txt"));
                   
                    bw.write(divider1);
                    bw.newLine();
                    for (String old : oldList) {
                        bw.write(old);
                        bw.newLine();
                    }
                    LocalDate dateListed = LocalDate.now();
                    LocalTime timeListed = LocalTime.now();

                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("EEEE, MM/dd/yyyy");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

                    String finalTimeListed = timeListed.format(formatter);
                    String finalDateListed = dateListed.format(formatter2);

                    String sellerName;

                    System.out.print("Enter seller name: ");
                    sellerName = user.nextLine();
                    System.out.println(" ");

                    bw.newLine();
                    bw.write("SELLER NAME: " + sellerName);
                    bw.newLine();
                    bw.write("DATE LISTED: " + finalDateListed);
                    bw.newLine();
                    bw.write("TIME LISTED: " + finalTimeListed);
                    bw.newLine();
                    bw.write(" ");
                    bw.newLine();

                    for (int i = 0; i < itemList.size(); i++) {
                        bw.write("Item name: " + itemList.get(i));
                        bw.newLine();
                        bw.write("Item price: " + priceList.get(i));
                        bw.newLine();
                        bw.write("Item quantity: " + quantityList.get(i));
                        bw.newLine();
                        bw.write("Total price for this item: " + totalPriceList.get(i));
                        bw.newLine();
                        bw.write("");
                        bw.newLine();
                    }
                    
                    double sum = totalAmountDueList.get(0) + oldDueList.get(0);
                    bw.write(" ");
                    bw.newLine();
                    bw.write(divider2);
                    bw.newLine();
                    bw.write("TOTAL AMOUNT DUE: " + sum);
                    bw.newLine();
                    bw.write(divider3);
                    bw.newLine();

                    for (String all : entireList) {
                        bw.write(all);
                        bw.newLine();
                    }
                    bw.close();

                    System.out.println(" ");
                    System.out.println("Items successfully added!");
                    break;
                }
            }
        }
    }
}