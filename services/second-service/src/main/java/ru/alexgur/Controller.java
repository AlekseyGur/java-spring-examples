package ru.alexgur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class Controller {

    @Autowired
    private Environment env;

    @GetMapping
    public Map<String, String> getAllSettings() {
        return ((AbstractEnvironment) env).getPropertySources()
                .stream()
                .flatMap(ps -> ((Map<String, Object>)ps.getSource()).entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toString()));
    }
}