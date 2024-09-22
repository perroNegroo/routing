package routing.model.graphmodel.utils;


/**
 * A utility class for parsing integer values from strings.
 *
 * @author uktup
 */
public final class ParseNumbers {
    private ParseNumbers() { }
    /**
     * Parses the specified string as an integer.
     *
     * @param number the string to be parsed
     * @return the integer value represented by the string, or
     *         {@link Integer#MIN_VALUE} if the string cannot be parsed
     *         as an integer due to a {@link NumberFormatException}.
     */
    public static int parseInteger(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return Integer.MIN_VALUE;
        }
    }
}
