import java.io.BufferedReader;
import java.io.IOException;

public class SafeInput {
    public static int getRangedInt(BufferedReader reader, String prompt, int min, int max) {
        int input;
        do {
            try {
                System.out.print(prompt);
                String inputStr = reader.readLine();
                input = Integer.parseInt(inputStr);
                if (input < min || input > max) {
                    System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                input = min - 1;
            }
        } while (input < min || input > max);
        return input;
    }

    public static String getNonEmptyString(BufferedReader reader, String prompt) {
        String input;
        do {
            try {
                System.out.print(prompt);
                input = reader.readLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Input cannot be empty. Please enter a valid value.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading input. Please try again.");
                input = "";
            }
        } while (input.isEmpty());
        return input;
    }

    public static String getRegExString(BufferedReader reader, String regex, String prompt) {
        String input;
        do {
            try {
                System.out.print(prompt);
                input = reader.readLine().trim();
                if (!input.matches(regex)) {
                    System.out.println("Invalid input. Please enter a valid value.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading input. Please try again.");
                input = "";
            }
        } while (!input.matches(regex));
        return input;
    }

    public static boolean getYNConfirm(BufferedReader reader, String prompt) {
        String input;
        do {
            try {
                System.out.print(prompt + " (Y/N): ");
                input = reader.readLine().trim().toUpperCase();
                if (!input.equals("Y") && !input.equals("N")) {
                    System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading input. Please try again.");
                input = "";
            }
        } while (!input.equals("Y") && !input.equals("N"));
        return input.equals("Y");
    }
}