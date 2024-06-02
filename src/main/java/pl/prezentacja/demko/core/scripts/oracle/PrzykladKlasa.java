package pl.prezentacja.demko.core.scripts.oracle;

import java.util.Map;

public class PrzykladKlasa {
    public String getValue() {
        return "test-Klasa";
    }

    public Integer processDane(String value) {
        return value.length();
    }

    public String processDane(Double value) {
        return value.toString() + "-test";
    }

    public String processDane(Object value) {
        return value.getClass() + " - is map " + (value instanceof Map);
    }
}
