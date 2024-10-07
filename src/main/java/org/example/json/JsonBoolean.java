package org.example.json;

public class JsonBoolean implements Json {

    private final boolean value;

    public JsonBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}