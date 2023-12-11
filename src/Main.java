import javax.swing.JFileChooser;
import java.io.*;
import java.util.ArrayList;

public class Main {
    private static ArrayList<String> itemList = new ArrayList<>();
    private static boolean needsToBeSaved = false;
    private static String currentFileName = "";
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        boolean quit = false;

        do {
            displayMenu();
            String choice = SafeInput.getRegExString(br, "[AaDdVvOoCcSsQq]", "Please enter your choice: ");

            switch (choice.toUpperCase()) {
                case "A":
                    addItem();
                    break;
                case "D":
                    deleteItem();
                    break;
                case "V":
                    printList();
                    break;
                case "O":
                    openList();
                    break;
                case "C":
                    clearList();
                    break;
                case "S":
                    saveList();
                    break;
                case "Q":
                    quit = confirmQuit();
                    break;
            }

        } while (!quit);

        System.out.println("Thanks for using the program!");
    }

    private static void displayMenu() {
        System.out.println("Menu:");
        System.out.println("A - Add an item to the list");
        System.out.println("D - Delete an item from the list");
        System.out.println("V - View the list");
        System.out.println("O - Open a list file from disk");
        System.out.println("C - Clear the list");
        System.out.println("S - Save the current list file to disk");
        System.out.println("Q - Quit");
    }

    private static void addItem() {
        String newItem = SafeInput.getNonEmptyString(br, "Please enter an item to add: ");
        itemList.add(newItem);
        needsToBeSaved = true;
        System.out.println("The item was successfully added!");
    }

    private static void deleteItem() {
        if (itemList.isEmpty()) {
            System.out.println("The list is empty. There is nothing to delete.");
            return;
        }

        printNumberedItems();
        int itemNumber = SafeInput.getRangedInt(br, "Enter the item number to delete: ", 1, itemList.size());

        String removedItem = itemList.remove(itemNumber - 1);
        needsToBeSaved = true;
        System.out.println("Item '" + removedItem + "' successfully deleted!");
    }

    private static void printList() {
        if (itemList.isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            System.out.println("Current List:");
            for (String item : itemList) {
                System.out.println("- " + item);
            }
        }
    }

    private static void clearList() {
        itemList.clear();
        needsToBeSaved = true;
        System.out.println("The list has been cleared.");
    }

    private static void printNumberedItems() {
        System.out.println("Numbered List:");
        for (int i = 0; i < itemList.size(); i++) {
            System.out.println((i + 1) + ". " + itemList.get(i));
        }
    }

    private static boolean confirmQuit() {
        if (needsToBeSaved) {
            boolean save = SafeInput.getYNConfirm(br, "You have unsaved changes. Do you want to save before quitting?");
            if (save) {
                saveList();
            }
        }
        return SafeInput.getYNConfirm(br, "Are you sure you want to quit the program?");
    }

    private static void openList() {
        if (needsToBeSaved) {
            boolean save = SafeInput.getYNConfirm(br, "You have unsaved changes. Do you want to save before opening a new list?");
            if (save) {
                saveList();
            }
        }

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            currentFileName = selectedFile.getName();
            loadList(selectedFile);
            System.out.println("List loaded successfully from " + currentFileName);
        } else {
            System.out.println("No file selected. Continuing with the current list.");
        }
    }

    private static void loadList(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            itemList.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                itemList.add(line);
            }
            needsToBeSaved = false;
        } catch (IOException e) {
            System.out.println("Error loading the list from the file: " + e.getMessage());
        }
    }

    private static void saveList() {
        if (currentFileName.isEmpty()) {
            currentFileName = SafeInput.getNonEmptyString(br, "Please enter a name for the list file (without extension): ");
            currentFileName += ".txt";
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(currentFileName))) {
            for (String item : itemList) {
                writer.println(item);
            }
            needsToBeSaved = false;
            System.out.println("List saved successfully to " + currentFileName);
        } catch (IOException e) {
            System.out.println("Error saving the list to the file: " + e.getMessage());
        }
    }
}