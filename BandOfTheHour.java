import java.util.Scanner;
/**
 *  Program to help organize where the musicians will stand when they play at away games.
 * @author Joey DeGori
 * @version 1.0
 */

/**
 * This class represents the band of the hour setup for a performance.
 */
public class BandOfTheHour {
    private static final Scanner keyboard = new Scanner(System.in);
    private static final int MAX_ROWS = 10;
    private static final int MAX_POSITIONS = 8;
    private static final int MAX_WEIGHT_PER_POSITION = 100;
    private static int numRows;
    private static int[] numPositions;
    private static int[][] weights;

    public static void main(String[] args) {
        initialize();
        char choice;
        do {
            System.out.println("\n(A)dd, (R)emove, (P)rint, e(X)it : ");
            choice = Character.toUpperCase(keyboard.next().charAt(0));
            switch (choice) {
                case 'A':
                    addMusician();
                    break;
                case 'R':
                    removeMusician();
                    break;
                case 'P':
                    printAssignment();
                    break;
                case 'X':
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while(choice != 'X');
        keyboard.close();
    }

    /**
     * Initializes the information for the band, the rows and positions.
     *
     */
    private static void initialize() {
        System.out.println("Welcome to the Band of the Hour\n-------------------------------");
        do {
            System.out.print("Please enter number of rows: ");
            numRows = keyboard.nextInt();
        } while (numRows <= 0 || numRows > MAX_ROWS);

        numPositions = new int[numRows];
        weights = new int[numRows][];
        for (int index = 0; index < numRows; index++) {
            char rowLabel = (char) ('A' + index);
            do {
                System.out.print("Please enter number of positions in row " + rowLabel + ": ");
                numPositions[index] = keyboard.nextInt();
            } while (numPositions[index] <= 0 || numPositions[index] > MAX_POSITIONS);
            weights[index] = new int[numPositions[index]];
        }
    }

    /**
     * Adds a musician to a position in the band setup.
     * Prompts the user to input the row letter, position number, and weight of the musician.
     * Checks if the entered row, position, and weight are valid and within the limit.
     * If the position is already occupied or the weight limit for the row would be exceeded, error messages are displayed.
     */
    private static void addMusician() {
        System.out.print("Please enter row letter (A-" + (char) ('A' + numRows - 1) + "): ");
        char row = Character.toUpperCase(keyboard.next().charAt(0));
        int rowIndex = row - 'A';
        if (rowIndex < 0 || rowIndex >= numRows) {
            System.out.println("Invalid row. Please try again.");
            return;
        }
        int[] rowWeights = weights[rowIndex];
        System.out.print("Please enter position number (1 to " + numPositions[rowIndex] + "): ");
        int position = keyboard.nextInt();
        if (position < 1 || position > numPositions[rowIndex]) {
            System.out.println("Invalid position. Please try again.");
            return;
        }
        if (rowWeights[position - 1] == 0) {
            System.out.print("Please enter weight (45.0 to 200.0): ");
            int weight = keyboard.nextInt();
            if (weight >= 45 && weight <= 200) {
                if (getRowTotalWeight(rowIndex) + weight <= MAX_WEIGHT_PER_POSITION * numPositions[rowIndex]) {
                        rowWeights[position - 1] = weight;
                        System.out.println("****** Musician added.");
                } else {
                    System.out.println("Row weight limit exceeded. Cannot add musician.");
                }
            } else {
                System.out.println("Invalid weight. Please enter a weight between 45 and 200.");
            }
        } else {
            System.out.println("Position " + position + " in row " + row + " is already occupied.");
        }
    }

    /**
     * Removes a musician from a position in the band setup.
     * Prompts the user to input the row letter and position number.
     * If the row or position is invalid or if there is no musician at the position,
     * error messages are displayed.
     */
    private static void removeMusician() {
        System.out.print("Enter the row letter (A-" + (char) ('A' + numRows - 1) + "): ");
        char row = Character.toUpperCase(keyboard.next().charAt(0));
        int rowIndex = row - 'A';
        if (rowIndex < 0 || rowIndex >= numRows) {
            System.out.println("Invalid row. Please try again.");
            return;
        }
        int[] rowWeights = weights[rowIndex];
        System.out.print("Enter the position number (1-" + numPositions[rowIndex] + "): ");
        int position = keyboard.nextInt();
        if (position < 1 || position > numPositions[rowIndex]) {
            System.out.println("Invalid position. Please try again.");
            return;
        }
        if (rowWeights[position - 1] != 0) {
            rowWeights[position - 1] = 0;
            System.out.println("Musician removed from position " + position + " in row " + row);
        } else {
            System.out.println("No musician found at position " + position + " in row " + row);
        }
    }

    /**
     * Prints the current assignment of weights for each position in the band setup.
     */
private static void printAssignment() {
    System.out.println("\nCurrent:");
    for (int index = 0; index < numRows; index++) {
        char rowLabel = (char) ('A' + index);
        int[] rowWeights = weights[index];
        System.out.print(rowLabel + ": ");
        for (int j = 0; j < numPositions[index]; j++) {
            System.out.printf("%6.1f ", (float) rowWeights[j]);
        }
        System.out.print("[");
        for (int j = 0; j < numPositions[index]; j++) {
            System.out.printf("%7.1f", (float) rowWeights[j]);
            if (j < numPositions[index] - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}
    /**
     * Calculates the total weight of all positions in the rows.
     *
     * @param rowIndex The index of the row to calculate the total weight.
     * @return The total weight of all positions in the row.
     */
    private static int getRowTotalWeight(int rowIndex) {
        int totalWeight = 0;
        for (int weight : weights[rowIndex]) {
            totalWeight += weight;
        }
        return totalWeight;
    }

}