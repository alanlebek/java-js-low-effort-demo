package pl.prezentacja.demko.core.scripts;


public interface JSScript {
    Object execute(String functionName, Object[] params);
}
