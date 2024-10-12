package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        try {
            var jsonString = getJsonString(args[0]);
            var jsonParser = new JsonParser();
            var parsedJson = jsonParser.parseJson(jsonString);
            System.out.println(parsedJson);
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "Parsing failed.", e);
        }
    }

    private static String getJsonString(String fileName) throws IOException {
        var inputStream = Files.newInputStream(new File(fileName).toPath());
        var resultStringBuilder = new StringBuilder();

        try (var br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) resultStringBuilder.append(line).append("\n");
        }

        return resultStringBuilder.toString();
    }
}