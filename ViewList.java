import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ViewList {
    private final ArrayList<String> list = new ArrayList<>();
    private final ArrayList<String> latestDue = new ArrayList<>();
    private String sukiName;
    private String fileName;

    public void setSukiName(String sukiName) {
        this.sukiName = sukiName;
    }

    public String getSukiName() {
        return sukiName;
    }

    public void setFileName(String fileName) {
        this.fileName = sukiName + ".txt";
    }

    public String getFileName() {
        return fileName;
    }

    public void viewList() throws IOException {
        SukiList sukiList = new SukiList();
        
        sukiList.sukiList();
        
        Scanner user = new Scanner(System.in);
        
        System.out.println("-------------------------------------------------------------------------------");
        System.out.print("Enter suki name: ");
        sukiName = user.nextLine();
        System.out.println("-------------------------------------------------------------------------------");

        String fileName = sukiName + ".txt";
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String ignore = "TOTAL AMOUNT DUE: ";
            String line;
            String lines = null;
            
            while ((line = br.readLine()) != null) {
                if (line.contains(ignore)) {
                    lines = line;
                } else {
                    list.add(line);
                }
            }
            
            if (lines != null) {
                latestDue.add(lines);
            }
            
            for (String debt : list) {
                System.out.println(debt);
            }

            if (latestDue.isEmpty() || latestDue.get(0) == null) {
                System.out.println("This suki has no balance.");
            } else {
                System.out.println(latestDue.get(0));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: The list for suki '" + sukiName + "' does not exist.");
        } catch (IOException e) {
            System.out.println("Error: An error occurred while reading the file.");
        }
    }
}
