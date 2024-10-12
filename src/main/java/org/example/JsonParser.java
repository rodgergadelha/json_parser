package org.example;

import org.example.json.*;

import java.util.Set;

import static java.lang.Character.isDigit;

public class JsonParser {

    private String jsonString;
    private char currentChar;
    private int jsonStringLength;
    private int currentTokenPosition;

    private static final String INVALID_JSON_MESSAGE = "Invalid JSON";
    private static final Set<Character> SKIP_CHARACTERS = Set.of(' ', '\n', '\t');

    public Json parseJson(String jsonString) {
        jsonStringLength = jsonString.length();
        if(jsonStringLength == 0) throw new IllegalArgumentException(INVALID_JSON_MESSAGE);
        this.jsonString = jsonString;
        currentTokenPosition = 0;
        currentChar = jsonString.charAt(0);
        return parseValue();
    }

    private void advance() {
        currentTokenPosition++;
        if(notFinished()) {
            currentChar = jsonString.charAt(currentTokenPosition);
            if(SKIP_CHARACTERS.contains(currentChar)) advance();
        }
    }

    private boolean notFinished() {
        return currentTokenPosition != jsonStringLength;
    }

    private Json parseValue() {
        if(currentChar == '{') return parseObject();

        if(currentChar == '[') return parseArray();

        if(currentChar == '"') return parseString();

        if(currentChar == '-' || isDigit(currentChar)) return parseNumber();

        if(currentChar == 't' || currentChar == 'f') return parseBoolean();

        if(currentChar == 'n') return parseNull();

        throw new IllegalArgumentException(INVALID_JSON_MESSAGE);
    }

    private Json parseObject() {
        var jsonObject = new JsonObject();
        advance();

        while(currentChar != '}' && notFinished()) {
            var fieldName = parseString();
            if(currentChar != ':') throw new IllegalArgumentException(INVALID_JSON_MESSAGE);
            advance();
            var fieldValue = parseValue();
            jsonObject.put(fieldName.value(), fieldValue);
            var hasComma = currentChar == ',';
            if(hasComma) advance();
            if(hasComma && currentChar == '}') throw new IllegalArgumentException(INVALID_JSON_MESSAGE);
        }

        if(currentChar != '}') throw new IllegalArgumentException(INVALID_JSON_MESSAGE);
        advance();
        return jsonObject;
    }

    private JsonArray parseArray() {
        var jsonArray = new JsonArray();
        advance();

        while(currentChar != ']' && notFinished()) {
            var element = parseValue();
            jsonArray.add(element);
            var hasComma = currentChar == ',';
            if(hasComma) advance();
            if(hasComma && currentChar == ']') throw new IllegalArgumentException(INVALID_JSON_MESSAGE);
        }

        if(currentChar != ']') throw new IllegalArgumentException(INVALID_JSON_MESSAGE);
        advance();
        return jsonArray;
    }

    private JsonString parseString() {
        var strBuilder = new StringBuilder();
        advance();

        while(currentChar != '"' && notFinished()) {
            strBuilder.append(currentChar);
            advance();
        }

        if(currentChar != '"' || strBuilder.isEmpty()) throw new IllegalArgumentException(INVALID_JSON_MESSAGE);
        advance();

        return new JsonString(strBuilder.toString());
    }

    private Json parseNumber() {
        var numberStr = new StringBuilder();
        var isFloat = false;

        while(currentChar != ',' && currentChar != '}' && currentChar != ']' && notFinished()) {
            if(currentChar == '.') isFloat = true;
            numberStr.append(currentChar);
            advance();
        }

        try {
            return isFloat ? new JsonFloat(Double.parseDouble(numberStr.toString()))
                    : new JsonInteger(Integer.parseInt(numberStr.toString()));

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_JSON_MESSAGE);
        }
    }

    private Json parseBoolean() {
        var booleanStr = new StringBuilder();

        while(currentChar != ',' && currentChar != '}' && currentChar != ']' && notFinished()) {
            booleanStr.append(currentChar);
            advance();
        }

        if(!booleanStr.toString().equals("true") && !booleanStr.toString().equals("false"))
            throw new IllegalArgumentException(INVALID_JSON_MESSAGE);

        return new JsonBoolean(Boolean.parseBoolean(booleanStr.toString()));
    }

    private Json parseNull() {
        var nullStr = new StringBuilder();

        while(currentChar != ',' && currentChar != '}' && currentChar != ']' && notFinished()) {
            nullStr.append(currentChar);
            advance();
        }

        if(!nullStr.toString().equals("null")) throw new IllegalArgumentException(INVALID_JSON_MESSAGE);

        return null;
    }
}