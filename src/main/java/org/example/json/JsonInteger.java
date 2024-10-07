package org.example.json;

public record JsonInteger(int value) implements Json {

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}