package org.example;

public class Main {
    public static void main(String[] args) {
        var jsonString = "{\"name\": \"Rodger\", \"age\": 21, \"single\": false, \"grades\": [10.0, 8.5, {\"age\": 23}]}";
        var jsonParser = new JsonParser();
        var parsedJson = jsonParser.parseJson(jsonString);
        System.out.println(parsedJson);
    }
}