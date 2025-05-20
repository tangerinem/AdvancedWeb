package com.springboot.webprogrammingmp.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class GeminiApiClient {

    private static final String GEMINI_API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";
    private final String apiKey;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public GeminiApiClient(@Value("${gemini.api.key}") String apiKey) { // application.properties 또는 yml에서 키를 읽어옴
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public String generateText(String prompt) throws IOException, InterruptedException {
        ObjectNode root = objectMapper.createObjectNode();
        var contentsArray = objectMapper.createArrayNode();
        var message = objectMapper.createObjectNode();
        message.put("role", "user");

        var partsArray = objectMapper.createArrayNode();
        partsArray.add(objectMapper.createObjectNode().put("text", prompt));
        message.set("parts", partsArray);

        contentsArray.add(message);
        root.set("contents", contentsArray);

        String requestUrl = GEMINI_API_URL + "?key=" + apiKey;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(root.toString()))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.err.println("Gemini API Error: Status Code " + response.statusCode());
            System.err.println("Response Body: " + response.body());
            throw new IOException("Gemini API returned non-200 status code: " + response.statusCode());
        }

        JsonNode rootNode = objectMapper.readTree(response.body());
        JsonNode textNode = rootNode.path("candidates").get(0).path("content").path("parts").get(0).path("text");

        if (textNode != null && textNode.isTextual()) {
            return textNode.asText();
        } else {
            System.err.println("Failed to extract text from Gemini API response: " + response.body());
            return "Error: Could not generate text.";
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        // 실제 API 키로 교체하세요! 환경 변수나 설정 파일에서 읽어오는 것을 권장합니다.
        String yourApiKey = System.getenv("AIzaSyDnK_X_-QdM654U0g6Kx_7i_iI2GQmobaQ"); // 예: 환경 변수에서 읽기

        if (yourApiKey == null || yourApiKey.isEmpty()) {
            System.err.println("GEMINI_API_KEY environment variable not set.");
            return;
        }

        GeminiApiClient apiClient = new GeminiApiClient(yourApiKey);

        try {
            String prompt = "Explain the difference between Java and JavaScript.";
            System.out.println("Sending prompt to Gemini: " + prompt);
            String generatedText = apiClient.generateText(prompt);
            System.out.println("\nGemini Response:\n" + generatedText);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
