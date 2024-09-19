package model.txtmanager.parameters;

import model.txtmanager.txt.TestTxt;

import java.util.List;

/**
 * Validates subnet masks.
 * @author uktup
 */
public class Mask implements TestTxt {
    @Override
    public boolean valid(List<String> data) {
        return false;
    }
    /**
     * Validates a subnet mask.
     *
     * @param mask the subnet mask to validate (e.g., "24")
     * @return true if the mask is valid, false otherwise
     */
    public static boolean maskValidator(String mask) {
        try {
            int intMask = Integer.parseInt(mask);
            if (intMask < 0 || intMask > 31) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
