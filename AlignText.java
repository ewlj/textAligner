import static java.lang.Integer.parseInt;

import java.util.List;

/**
 * This class processes the arguments supplied by the user and passes them to the TextAligner.
 * If an align mode is not specified program will default to right aligned
 *
 * @author ewlj
 */

public class AlignText {
    /**
     * Main method to check the input arguments are correct and pass to textAligner.
     *
     * @param args user entered arguments.
     */
    public static void main(String[] args) {
        try {
            //Try to parse lineLength from arguments and check for errors in the input
            //of the alignMode and pass to to TextAligner.
            int lineLength = parseInt(args[1]);
            List<String> linesInText;
            String text = args[0];
            if (lineLength < 0) {
                throw new Exception();
            } else if (args.length == 2) {
                TextAligner ta = new TextAligner(text, lineLength);
                linesInText = ta.textAligner();
                ta.printText(linesInText);
            } else if (args[2].equalsIgnoreCase("R") || args[2].equalsIgnoreCase("L")
                    || args[2].equalsIgnoreCase("J") || args[2].equalsIgnoreCase("C")) {
                TextAligner ta = new TextAligner(text, lineLength, args[2]);
                linesInText = ta.textAligner();
                ta.printText(linesInText);
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("usage: java AlignText file_name line_length <align_mode>");
            //If exception occurs the above message will be shown and the program will terminate.
        }
    }
}
