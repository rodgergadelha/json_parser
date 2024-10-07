package org.example.json;

import java.util.LinkedHashMap;
import java.util.Map;

public class JsonObject implements Json {

    private final Map<String, Json> fieldMap = new LinkedHashMap<>();

    public void put(String key, Json value) {
        fieldMap.put(key, value);
    }

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        fieldMap.forEach((key, value) -> {
            stringBuilder.append("\"").append(key).append("\":").append(value).append(",");
        });
        if(!fieldMap.isEmpty()) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}