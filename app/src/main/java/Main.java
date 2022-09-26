
import java.io.IOException;
import java.util.Scanner;

/**
 * test
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Scanner kb = new Scanner(System.in);
        boolean kContinue = true;
        System.out.println("Welcome to Civic Info Finder!");
        System.out.println("This is a program that serves as a client for the Google Civic Information API");
        System.out.println(
                "Lets begin by making a User object, please type in your address (Street Address, City State)");
        String address = kb.nextLine();
        User user = new User(address);
        API_Runner runner = new API_Runner(user);
        int menu = 0;
        boolean location;
        boolean office;
        int stoVal1 = 0;
        int stoVal2 = 0;
        do {
            switch (menu) {
                case 0:
                    System.out.println(
                            "This is the menu\nPress 0 for all your options\nPress 1 to view all elections\nPress 2 to find voterInfo\nPress 3 to find representative info\nPress 4 to view OCD IDs\nPress 5 to exit");
                    menu = kb.nextInt();
                    break;
                case 1:
                    System.out.println(
                            "This option will output all the elections in the database (WARNING: Output can get very large\nSaving to file might be necessary)\nPress 'y' to continue");
                    if (kb.next().equals("y")) {
                        System.out.println(runner.viewElections());
                        System.out.println("\n\nWould you like to save to file (Press 'y' or 'n')");
                        if (kb.next().equals("y")) {
                            runner.saveToFile(runner.x);
                            System.out.println("Saved to test.json");
                        }
                    }
                    System.out.println(
                            "\nPress 0 for all your options\nPress 1 to view all elections\nPress 2 to find voterInfo\nPress 3 to find representative info\nPress 4 to view OCD IDs\nPress 5 to exit");
                    menu = kb.nextInt();
                    break;
                case 2:
                    System.out.println(
                            "This option will output all voterInfo in the database (polling locations, election sites, etc.)\nWARNING: Output can get very large saving to file might be necessary)\nPress 'y' to continue");
                    if (kb.next().equals("y")) {
                        System.out.println(runner.voterInfo(true));
                        System.out.println("\n\nWould you like to save to file (Press 'y' or 'n')");
                        if (kb.next().equals("y")) {
                            runner.saveToFile(runner.x);
                            System.out.println("Saved to test.json");
                        }
                    }
                    System.out.println(
                            "\nPress 0 for all your options\nPress 1 to view all elections\nPress 2 to find voterInfo\nPress 3 to find representative info\nPress 4 to view OCD IDs\nPress 5 to exit");
                    menu = kb.nextInt();
                    break;
                case 3:
                    System.out.println(
                            "This option will output all kinds of information about your local and national representatives\nWARNING: Output can get very large saving to file might be necessary)\nPress 'y' to continue");
                    if (kb.next().equals("y")) {
                        System.out
                                .println("Because of how large this response can be, there are modifiers you can use");
                        System.out
                                .println("The two modifiers are based on location (local v national) and office held");
                        System.out.println("Would you like to use a location modifier? ('y' or 'n')");
                        if (kb.next().equals("y")) {
                            location = true;
                            System.out.println("Please select an option from below");
                            int k = 0;
                            for (String string : runner.choices) {

                                System.out.println("Option " + k + "--" + string);
                                k++;
                            }
                            stoVal1 = kb.nextInt();
                        } else {
                            location = false;
                        }
                        System.out.println("Would you like to use a office modifier? ('y' or 'n')");
                        if (kb.next().equals("y")) {
                            office = true;
                            System.out.println("Please select an option from below");
                            int k = 0;
                            for (String string : runner.officeValues) {

                                System.out.println("Option " + k + "--" + string);
                                k++;
                            }
                            stoVal2 = kb.nextInt();
                        } else {
                            office = false;
                        }
                        System.out.println(runner.repInfo(stoVal1, stoVal2, location, office));
                        System.out.println("\n\nWould you like to save to file (Press 'y' or 'n')");
                        if (kb.next().equals("y")) {
                            runner.saveToFile(runner.x);
                            System.out.println("Saved to test.json");
                        }
                    }
                    System.out.println(
                            "\nPress 0 for all your options\nPress 1 to view all elections\nPress 2 to find voterInfo\nPress 3 to find representative info\nPress 4 to view OCD IDs\nPress 5 to exit");
                    menu = kb.nextInt();
                    break;
                case 4:
                    System.out.println("This option prints out all the OCD IDs associated with your addresss");
                    System.out.println("Whats an OCD ID (Open Civic Data Identifier) is an identifier that tells the API all the different jurisdictions you fall under");
                    System.out.println("An ");
                    String[] ocdID = runner.getOCD();
                    for (String string : ocdID) {
                        System.out.println(string);
                    }
                    break;
                case 5:
                    kContinue = false;
                    break;
                default:
                    break;
            }
        } while (kContinue);
        kb.close();
    }

}
