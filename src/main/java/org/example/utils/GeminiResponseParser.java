package org.example.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GeminiResponseParser {


    public static List<String> parseLocatorSuggestions(String response) throws Exception {
        List<String> suggestions = new ArrayList<>();

        if (response == null || response.trim().isEmpty()) {
            throw new Exception("Empty response from Gemini API");
        }

        JSONObject jsonResponse = new JSONObject(response);
        JSONArray candidates = jsonResponse.getJSONArray("candidates");

        for (int i = 0; i < candidates.length(); i++) {
            JSONObject candidate = candidates.getJSONObject(i);
            JSONObject content = candidate.getJSONObject("content");
            JSONArray parts = content.getJSONArray("parts");

            for (int j = 0; j < parts.length(); j++) {
                String text = parts.getJSONObject(j).getString("text");

                // Handle case where Gemini wraps JSON in markdown code block
                if (text.startsWith("```json")) {
                    int start = text.indexOf("{");
                    int end = text.lastIndexOf("}");
                    if (start != -1 && end != -1 && start < end) {
                        text = text.substring(start, end + 1);
                    } else {
                        throw new Exception("Failed to extract JSON from Gemini response");
                    }
                }

                // Parse locators from JSON
                JSONObject jsonObject = new JSONObject(text);
                JSONArray locatorsArray = jsonObject.getJSONArray("locators");

                for (int k = 0; k < locatorsArray.length(); k++) {
                    suggestions.add(locatorsArray.getString(k).trim());
                }
            }
        }

        return suggestions;
    }


}
