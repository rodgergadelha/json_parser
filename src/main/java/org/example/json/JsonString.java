package org.example.json;

public record JsonString(String value) implements Json {

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }
}