package com.springboot.webprogrammingmp.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class ChatService {

    private final GeminiApiClient geminiApiClient;

    @Autowired
    public ChatService(GeminiApiClient geminiApiClient) {
        this.geminiApiClient = geminiApiClient;
    }

    public String recommendRecipePrompt(String userInput) {
        String[] ingredients = userInput.split(",");
        String prompt = String.join(", ",
                Arrays.stream(ingredients).map(String::trim).toList()
        ) + "로 만들 수 있는 레시피 3가지를 알려줘.";

        try {
            return geminiApiClient.generateText(prompt);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "API 호출 중 오류가 발생했습니다.";
        }
    }
}
