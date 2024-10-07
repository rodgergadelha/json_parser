package org.example.json;

import java.util.LinkedList;
import java.util.List;

public class JsonArray implements Json {

    private final List<Json> elements = new LinkedList<>();

    public void add(Json element) {
        elements.add(element);
    }

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (var element : elements) {
            stringBuilder.append(element);
            stringBuilder.append(", ");
        }
        if (!elements.isEmpty()) {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}