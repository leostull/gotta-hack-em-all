import java.util.Scanner; //imports scanner class, to read user input
import java.util.HashSet; //To store elements with no duplicates 
import java.util.Set; //Does not allow duplicates, each allergen can only be stored once.

public class TrackAllergy {
    
    // Set to store allergies which are flagged by the user
    private static Set<String> flaggedAllergies = new HashSet<>();

    public static void main(String[] args) { //args= array of strings


        Scanner scanner = new Scanner(System.in); //allows us to read user input from the terminal
        
        // Continuously ask user for allergies and flagging these
        while (true) {
            System.out.print("Please enter allergy. Type 'exit' to stop: ");
            String allergy = scanner.nextLine()//.trim();

            //Exiting tracking
            if (allergy.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the program...");
                break;
            }
            
            // Flag the allergy (adding it to a set)
            trackAllergy(allergy);
            
            //API call (Groq) to track the allergy
            System.out.println("The allergy '" + allergy + "' is now flagged.");
            // api.trackAllergy(allergy);
        }
        
        scanner.close();
    }

    public static void trackAllergy(String allergy) {
        flaggedAllergies.add(allergy);
    }

    //Getting flagged allergies (use actual API)
    public static Set<String> getFlaggedAllergies() {
        return flaggedAllergies;
    }
}

