package org.example.ai;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

public class GeminiAgent {

    private static final String GEMINI_API_KEY;

    static {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/config.properties"));
            GEMINI_API_KEY = properties.getProperty("gemini.api.key");
            if (GEMINI_API_KEY == null) {
                throw new IllegalStateException("Gemini API key missing in config.properties");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }


    public static String callGemini(String prompt) throws Exception {
        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + GEMINI_API_KEY;

        String requestBody = "{ \"contents\": [{ \"parts\": [{ \"text\": \"" + escapeJson(prompt) + "\" }] }] }";

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(requestBody.getBytes());
        }

        int responseCode = connection.getResponseCode();
        System.out.println("Gemini API Response Code: " + responseCode);

        if (responseCode != 200) {
            InputStream errorStream = connection.getErrorStream();
            String errorResponse = new Scanner(errorStream).useDelimiter("\\A").next();
            System.out.println("Gemini API Error Response: " + errorResponse);
            throw new RuntimeException("Gemini API request failed with response code: " + responseCode + ". Error: " + errorResponse);
        }

        Scanner scanner = new Scanner(connection.getInputStream());
        String response = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";

        scanner.close();

        System.out.println("Raw Gemini API Response: " + response);  // <--- Add this to see what's coming back
        return response;
    }


    private static String escapeJson(String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }
}
