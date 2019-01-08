import java.util.ArrayList;
import java.util.List;

/**
 * This class reads in a text file through the use of the FileUtil class, and
 * aligns text based on line length and align mode. If an align mode is not specified
 * program will default to right aligned.
 *
 * @author ewlj
 */
public class TextAligner {

    private String text;
    private int lineLength;
    private String alignMode;

    /**
     * Constructor for cases where alignMode is specified.
     *
     * @param fileName The text file to be read in
     * @param length   The desired line length
     * @param mode     The alignment mode
     */
    public TextAligner(String fileName, int length, String mode) {
        text = fileName;
        lineLength = length;
        alignMode = mode;
    }

    /**
     * Constructor for cases where alignMode is not specified.
     *
     * @param fileName The text file to be read in
     * @param length   The desired line length
     */
    public TextAligner(String fileName, int length) {
        text = fileName;
        lineLength = length;
        alignMode = "R";
    }

    /**
     * A method to align text to the correct alignMode based on input.
     *
     * @return Returns a list containing the formatted text
     */
    List<String> textAligner() {
        String[] paragraphs = FileUtil.readFile(this.text);
        List<String> completeText = new ArrayList<>();
        List<String> linesInText;
        if (alignMode.equalsIgnoreCase("R")) {
            for (String newParagraph : paragraphs) {
                linesInText = generateLines(newParagraph, lineLength);
                rightAlign(linesInText, lineLength);
                completeText.addAll(linesInText);
            }
        } else if (alignMode.equalsIgnoreCase("C")) {
            for (String newParagraph : paragraphs) {
                linesInText = generateLines(newParagraph, lineLength);
                centreAlign(linesInText, lineLength);
                completeText.addAll(linesInText);
            }
        } else if (alignMode.equalsIgnoreCase("L")) {
            for (String newParagraph : paragraphs) {
                linesInText = generateLines(newParagraph, lineLength);
                completeText.addAll(linesInText);
            }
        } else if (alignMode.equalsIgnoreCase("J")) {
            for (String newParagraph : paragraphs) {
                linesInText = generateLines(newParagraph, lineLength);
                justifyText(linesInText, lineLength);
                completeText.addAll(linesInText);
            }
        }
        return (completeText);
    }

    /**
     * Takes the input from FileUtil and generates lines of length less than or equal to the lineLength for each paragraph.
     *
     * @param newParagraph String representing a single paragraph
     * @param lineLength   Line length specified by user
     * @return List of Strings representing the lines contained within the text.
     */
    private static List<String> generateLines(String newParagraph, int lineLength) {
        List<String> linesInText = new ArrayList<>();
        String[] paragraph = newParagraph.split(" ");
        String currentLine = "";
        for (int i = 0; i < paragraph.length; i++) {
            if (currentLine.equals("")) { //Starts a new line.
                currentLine = paragraph[i];
            } else if (currentLine.length() + paragraph[i].length() < lineLength) {
                currentLine = currentLine.concat(" ".concat(paragraph[i])); //Add word at current position to line.
            } else {
                linesInText.add(currentLine); //Starts a new line
                currentLine = paragraph[i];
            }
            if (i == paragraph.length - 1) {
                linesInText.add(currentLine); //Add line to List if no more index values remaining.
            }
        }
        return linesInText;
    }

    /**
     * Takes the List generated by generateLines and pads the lines with preceding spaces to right align the text.
     *
     * @param linesInText Lines of length less than or equal to the lineLength
     * @param lineLength  Line length specified by user
     */
    private static void rightAlign(List<String> linesInText, int lineLength) {
        for (int i = 0; i < linesInText.size(); i++) {
            String line = linesInText.get(i);
            StringBuilder sb = new StringBuilder();
            while ((sb.length() + line.length()) < lineLength) {
                sb.append(" "); // Pads the line with the required number of spaces.
            }
            sb.append(line);
            linesInText.set(i, sb.toString());
        }

    }

    /**
     * Takes the List generated by generateLines and pads the lines with preceding spaces to centre the text.
     *
     * @param linesInText Lines of length less than or equal to the lineLength
     * @param lineLength  Line length specified by user
     */
    private static void centreAlign(List<String> linesInText, int lineLength) {
        for (int i = 0; i < linesInText.size(); i++) {
            String line = linesInText.get(i);
            int leadingSpaces;
            if (line.length() >= lineLength) {
                leadingSpaces = 0;
            } else if (line.length() % 2 != 0 && lineLength - line.length() > 2) {
                leadingSpaces = (lineLength - line.length()) / 2 + 1; // Additional leading space in case of odd number
            } else {
                leadingSpaces = (lineLength - line.length()) / 2; // Adds leading whitespace to line.
            }
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < leadingSpaces; j++) {
                sb.append(" ");
            }
            sb.append(line);
            linesInText.set(i, sb.toString());
        }
    }

    /**
     * Takes the list generated by generateLines and ands the correct number of spaces between the words to
     * justify the text.
     *
     * @param linesInText Lines of length less than or equal to the lineLength
     * @param lineLength  Line length specified by user
     */
    private static void justifyText(List<String> linesInText, int lineLength) {
        for (int i = 0; i < linesInText.size(); i++) {
            String line = linesInText.get(i);
            int wordsInLine = line.split(" ").length;
            int gapsInLine = wordsInLine - 1;
            int extraSpaces = lineLength - line.length();
            int totalSpaces = extraSpaces + gapsInLine;
            if (line.length() >= lineLength || wordsInLine == 1 || i == linesInText.size() - 1) {
                linesInText.set(i, line);
            } else {
                String[] words = line.split(" ");

                int standardSpaces = totalSpaces / gapsInLine;
                int additionalSpaces = totalSpaces % gapsInLine;
                StringBuilder sb = new StringBuilder(words[0]);
                for (int j = 1; j <= (gapsInLine - additionalSpaces); j++) {
                    //Adds the standard number of spaces to the leftmost gaps
                    for (int k = 0; k < standardSpaces; k++) {
                        sb.append(" ");
                    }
                    sb.append(words[j]);
                    line = sb.toString();
                }
                for (int j = (gapsInLine - additionalSpaces) + 1; j < wordsInLine; j++) {
                    //Adds the standard number of spaces+1 to the remaining gaps between words
                    for (int k = 0; k < standardSpaces + 1; k++) {
                        sb.append(" ");
                    }
                    sb.append(words[j]);
                    line = sb.toString();
                }
                linesInText.set(i, line);
            }
        }
    }

    /**
     * Prints the list of lines present in the input list.
     *
     * @param linesInText formatted lines to be printed
     */
    void printText(List<String> linesInText) {
        for (String str : linesInText) {
            System.out.println(str);
        }
    }
}