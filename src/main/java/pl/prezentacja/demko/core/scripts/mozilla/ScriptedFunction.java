package pl.prezentacja.demko.core.scripts.mozilla;

import org.mozilla.javascript.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.prezentacja.demko.core.scripts.JSScript;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

public class ScriptedFunction implements JSScript {
    private static final Logger log = LoggerFactory.getLogger(ScriptedFunction.class);
    private final Path path;

    public ScriptedFunction(Path path) {
        super();
        this.path = path;
    }

    public Object execute(String functionName, Object[] params) {
        var context = Context.enter();
        try {
            var scope = context.initStandardObjects();
            initCustomObjects(context, scope);

            var code = new String(Files.readAllBytes(path));
            var sourceName = String.format("<%s>", path);
            context.evaluateString(scope, code, sourceName, 1, null);

            var function = (Function) scope.get(functionName, scope);
            var result = function.call(context, scope, scope, params);
            if (result instanceof NativeJavaObject) {
                var date = (Date) Context.jsToJava(result, Date.class);
                return date.getTime() + " - unix time";
            }

            return Context.toString(result);
        } catch (IOException e) {
            log.error("Error while executing function", e);
        } finally {
            Context.exit();
        }

        return -1;
    }

    protected void initCustomObjects(Context context, Scriptable scope) {
        try {
            ScriptableObject.defineClass(scope, PrzykladHosted.class);
        } catch (Exception e) {
            log.error("Failed to initialize custom objects", e);
        }
    }
}
