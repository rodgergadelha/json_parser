package org.example.json;

public record JsonFloat(double value) implements Json {

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}