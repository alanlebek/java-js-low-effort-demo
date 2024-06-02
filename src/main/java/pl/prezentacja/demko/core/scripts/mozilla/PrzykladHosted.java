package pl.prezentacja.demko.core.scripts.mozilla;

import org.mozilla.javascript.annotations.JSConstructor;
import org.mozilla.javascript.annotations.JSFunction;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSGetter;

public class PrzykladHosted extends ScriptableObject {
    private static final long serialVersionUID = 1L;
    private int value;

    public PrzykladHosted() {}
    @JSConstructor
    public PrzykladHosted(int value) {
        this.value = value;
    }

    public String getClassName(){
        return "PrzykladHosted";
    }

    @JSFunction
    public void funkcja() {
        System.out.println("Wywołana z JS'a!!");
    }

    @JSFunction
    public void test(Object value) {
        System.out.println("PrzykladHosted.test");
        if (value instanceof String) {
            test((String) value);
        } else if (value instanceof Integer) {
            test((Integer) value);
        } else if (value instanceof Double) {
            test((Double) value);
        }
    }

    public void test(String value) {
        System.out.println("Called with string: " + value);
    }

    public void test(Integer value) {
        System.out.println("Called with int: " + value);
    }

    public void test(Double value) {
        System.out.println("Called with double: " + value);
    }

    @JSGetter
    public int getValue() {
        return value;
    }
}