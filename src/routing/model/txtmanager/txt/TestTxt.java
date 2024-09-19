package routing.model.txtmanager.txt;

import java.util.List;

/**
 * Interface for validating text data.
 * @author uktup
 */
public interface TestTxt {
    /**
     * Validates the given list of data.
     *
     * @param data the list of data to validate
     * @return true if the data is valid, false otherwise
     */
    boolean valid(List<String> data);
}
