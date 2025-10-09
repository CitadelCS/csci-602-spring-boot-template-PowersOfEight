package edu.citadel.main.cucumber;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CucumberTestContext {
    private final Map<String, Object> data = new HashMap<>();
    public void put(String key, Object value) { data.put(key, value); }
    public <T> T get(String key, Class<T> type) { return type.cast(data.get(key)); }
    public boolean containsKey(String key) { return data.containsKey(key); }
}
