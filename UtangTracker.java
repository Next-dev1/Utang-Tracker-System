import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UtangTracker {
    public static void menu() throws IOException {
        Scanner input = new Scanner(System.in);

        String choice;
        System.out.print("PRESS B TO GO BACK TO MENU OR PRESS P TO PAY: ");
        choice = input.nextLine();

        if (choice.equalsIgnoreCase("B")) {
            main(new String[0]);
        } else if (choice.equalsIgnoreCase("P")) {
            SukiPayment payment = new SukiPayment();
            payment.payment();
        } else {
            System.out.println("Invalid choice.");
            menu();
        }
    }

    public static void header() {
        LocalDate date = LocalDate.now();
        LocalDate day = LocalDate.now();

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE");

        String date1 = date.format(formatter);
        String day2 = day.format(formatter2);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("                         MAMA LINDA'S SARI-SARI STORE ");
        System.out.println("                                UTANG TRACKER");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("                                 " + day2);
        System.out.println("                                   " + date1.toUpperCase());
        System.out.println("===============================================================================");
    }

    public static void main(String[] args) throws IOException {
        Scanner user = new Scanner(System.in);
        String userChoice; // Change userChoice to String
        do {
            header();
            System.out.println("What would you like to do today?");
            System.out.println(" ");
            System.out.println("1.) Create new list");
            System.out.println("2.) Add to existing list");
            System.out.println("3.) View lists");
            System.out.println("4.) Suki payment");
            System.out.println("5.) Exit program");
            System.out.println(" ");
            System.out.print("Answer: ");
            userChoice = user.nextLine(); // Read input as String
            System.out.println("-------------------------------------------------------------------------------");

            switch (userChoice) {
                case "1": // Case strings should match user input strings
                    CreateList createList = new CreateList();
                    createList.enterItem();
                    menu();
                    break;

                case "2":
                    AddList addList = new AddList();
                    addList.adding();
                    menu();
                    break;

                case "3":
                    ViewList viewList = new ViewList();
                    viewList.viewList();
                    menu();
                    break;

                case "4":
                    SukiPayment sukiPayment = new SukiPayment();
                    sukiPayment.payment();
                    menu();
                    break;

                case "5":
                    System.out.println("                                Program exited");
                    System.out.println("===============================================================================");
                    break;

                default:
                    System.out.println("Invalid choice.");
                    menu();
                    break;
            }
        } while (!userChoice.equals("5")); // Check for string equality
    }
}
