package pl.prezentacja.demko.core.scripts.oracle;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.io.IOAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.prezentacja.demko.core.scripts.JSScript;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ScriptedFunction implements JSScript {
    private static final Logger log = LoggerFactory.getLogger(ScriptedFunction.class);
    private final Path path;

    public ScriptedFunction(Path path) {
        super();
        this.path = path;
    }

    public Object execute(String functionName, Object[] params) {
        try (var context = Context.newBuilder("js")
                .allowAllAccess(true)
                .allowHostAccess(HostAccess.ALL)
                .allowHostClassLookup(lookupWhiteList::contains)
                .allowIO(IOAccess.newBuilder().fileSystem(new MyFileSystem()).build())
                .option("js.ecmascript-version", "2022")
//                .option("inspect", "4242")
//                .option("inspect.Path", java.util.UUID.randomUUID().toString())
//                .option("inspect.WaitAttached", "false")
                .build();
        ) {
            var code = new String(Files.readAllBytes(path));
            var sourceName = String.format("%s", path.toString().replace(".js", ".mjs"));
            context.eval(Source.newBuilder("js", code, sourceName).build());

            var function = context.getBindings("js").getMember(functionName);
            var result = function.execute(params);
            if (result.isHostObject()) {
                var date = (Date) result.asHostObject();
                return date.getTime() + " - unix time";
            }
            return result.toString();

        } catch (IOException e) {
            log.error("Error while executing function", e);
        }

        return -1;
    }

    private static final List<String> lookupWhiteList = List.of(
            "java.util.Date",
            "pl.prezentacja.demko.core.scripts.mozilla.PrzykladHosted",
            "pl.prezentacja.demko.core.scripts.oracle.PrzykladKlasa"
    );
}
