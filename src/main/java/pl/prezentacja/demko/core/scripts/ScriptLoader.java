package pl.prezentacja.demko.core.scripts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class ScriptLoader {
    private static final Logger log = LoggerFactory.getLogger(ScriptLoader.class);
    private static final Path scriptBaseDirectory = Path.of("./scripts");
    private static final Map<String, Map<String, JSScript>> scripts = new HashMap<>();

    public ScriptLoader() {
        this.loadScripts();
    }

    private void loadScripts() {
        log.info("Loading scripts...");
        var scriptsMap = getScriptsMap(scriptBaseDirectory);
        scriptsMap.forEach((name, path) -> {
            log.info("Loading script {} ...", name);
            var rhinoScript = new pl.prezentacja.demko.core.scripts.mozilla.ScriptedFunction(path);
            var graalScript = new pl.prezentacja.demko.core.scripts.oracle.ScriptedFunction(path);
            var map = new HashMap<String, JSScript>() {{
                put("oracle", graalScript);
                put("mozilla", rhinoScript);
            }};
            scripts.put(name, map);
        });
    }

    private Map<String, Path> getScriptsMap(Path dir) {
        var scripts = new HashMap<String, Path>();
        var pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**.js");
        try {
            Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                    if (pathMatcher.matches(path)) {
                        var pathRelative = dir.relativize(path);
                        scripts.put(pathRelative.toString(), path);
                        log.info("Found script: {}", pathRelative.toString());
                    }

                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            log.error("Failed to load scripts: {}", e.getMessage());
            return scripts;
        }

        return scripts;
    }

    public JSScript getScript(String name, String engine) {
        var script = scripts.get(name);
        return script.get(engine);
    }
}
