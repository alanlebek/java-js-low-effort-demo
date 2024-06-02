package pl.prezentacja.demko.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.prezentacja.demko.core.scripts.ScriptLoader;

@RequestMapping(value = "/scripts")
@RestController
public class ExampleController {

    private static final Logger log = LoggerFactory.getLogger(ExampleController.class);
    @Autowired
    private ScriptLoader scriptLoader;


    @PostMapping("/call/{script}")
    public Object testScript(
            @PathVariable(name = "script", required = true) String script,
            @RequestParam(defaultValue = "") String methodName,
            @RequestParam(defaultValue = "") String params,
            @RequestParam(defaultValue = "mozilla") String engine
    ) {
        var function = scriptLoader.getScript(script, engine);
        if (function == null) {
            return -1;
        }

        return function.execute(methodName, new Object[]{params});
    }
}
