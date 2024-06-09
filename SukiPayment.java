import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SukiPayment {
    private final ArrayList<Double> lastDue = new ArrayList<>();
    private final ArrayList<Double> pay = new ArrayList<>();

    public void payment() throws IOException {
        Scanner user = new Scanner(System.in);
        ViewList viewList = new ViewList();

        viewList.viewList();
        String sukiName = viewList.getSukiName();
        
        // Loop to ensure a valid suki file is entered
        while (true) {
            String file = sukiName + ".txt";
            File f = new File(file);
            if (!f.exists()) {
                System.out.println("Error: The file " + file + " does not exist.");
                UtangTracker.menu(); // Prompt user to go back to menu or re-enter
                return;
            }

            double payment = 0;
            boolean validInput = false;

            while (!validInput) {
                System.out.println("-------------------------------------------------------------------------------");
                System.out.print("Enter amount to pay: ");
                try {
                    payment = user.nextDouble();
                    validInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a numeric value.");
                    user.next(); // Clear invalid input
                }
            }

            System.out.println("-------------------------------------------------------------------------------");
            user.nextLine(); // Consume newline left-over
            String confirm;
            validInput = false;

            while (!validInput) {
                System.out.println("Is the entered amount correct? (YES/NO): ");
                System.out.println("-------------------------------------------------------------------------------");
                confirm = user.nextLine().trim();

                if (confirm.equalsIgnoreCase("no")) {
                    validInput = true;
                } else if (confirm.equalsIgnoreCase("yes")) {
                    pay.add(payment);
                    validInput = true;

                    BufferedReader br = new BufferedReader(new FileReader(file));
                    
                    String last = "TOTAL AMOUNT DUE: ";
                    String line;
                    String lines = null;

                    while ((line = br.readLine()) != null) {
                        if (line.contains(last)) {
                            lines = line;
                        }
                    }
                    br.close();

                    if (lines != null) {
                        lastDue.add(Double.valueOf(lines.substring(last.length()).trim()));
                    } else {
                        System.out.println("This suki has no balance.");
                        UtangTracker.menu();
                        return;
                    }

                    double newBalance = lastDue.get(0) - pay.get(0);

                    if (newBalance == 0.0) {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
                        System.out.println("REMAINING BALANCE: " + newBalance + " PHP");
                        System.out.println("-------------------------------------------------------------------------------");
                        writer.close();
                        break;
                    } else {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
                        System.out.println("REMAINING BALANCE: " + newBalance + " PHP");
                        System.out.println("-------------------------------------------------------------------------------");
                        bw.append(" ");
                        bw.newLine();
                        bw.append("TOTAL AMOUNT DUE: " + newBalance);
                        bw.newLine();
                        bw.close();
                        break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter 'YES' or 'NO'.");
                }
            }

            if (validInput && pay.size() > 0) {
                break; // Exit the loop if a valid payment has been confirmed and processed
            }
        }
    }
}
