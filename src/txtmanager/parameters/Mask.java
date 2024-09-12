package txtmanager.parameters;

import txtmanager.txt.Test_Txt;

import java.util.List;

public class Mask implements Test_Txt {
    @Override
    public boolean valid(List<String> data) {
        return false;
    }
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
