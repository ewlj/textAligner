import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Alternative implementation to allow for input of multiple texts.
 *
 * @author ewlj
 */
public class InteractiveAlign {

    /**
     * Takes user input using scanner to format the text.
     *
     * @param args Program arguments
     */
    public static void main(String[] args) {

        while (true) {
            Scanner reader = new Scanner(System.in);
            int lineLength = 0;
            String alignMode;
            List<String> linesInText;
            System.out.print("Enter the file name of the text file: ");
            String textFile = reader.next();
            System.out.print("Enter the desired line length: ");
            while (lineLength < 1) {
                try { // To handle non integer values
                    lineLength = reader.nextInt();
                    if (lineLength < 1) {
                        System.out.print("Please enter a valid line length: ");
                    }
                } catch (InputMismatchException e) {
                    System.out.print("Please enter a valid line length: ");
                    reader.next();
                }
            }
            System.out.print("Enter the align mode: ");
            while (true) { // Forces user to enter a valid alignment mode
                alignMode = reader.next();
                if (alignMode.equalsIgnoreCase("R") || alignMode.equalsIgnoreCase("L")
                        || alignMode.equalsIgnoreCase("J") || alignMode.equalsIgnoreCase("C")) {
                    System.out.println();
                    break;
                } else {
                    System.out.print("Please re-enter a valid align mode (R/L/C/J): ");
                }
            }

            TextAligner ta = new TextAligner(textFile, lineLength, alignMode);
            linesInText = ta.textAligner();
            ta.printText(linesInText);
            System.out.println();
            System.out.print("Enter a new text? Y/N: ");
            String s = reader.next();
            if (s.equalsIgnoreCase("N")) {
                break;
            }
        }
    }
}
