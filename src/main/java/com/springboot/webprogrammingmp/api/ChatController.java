package com.springboot.webprogrammingmp.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ChatController {
    private final GeminiApiClient geminiApiClient; // Gemini API 클라이언트
    private final ChatService chatService;

    // 이전 채팅 기록을 저장할 리스트 (임시)
    private final List<Map<String, String>> chatHistory = new ArrayList<>();

    public ChatController(GeminiApiClient geminiApiClient, ChatService chatService) {
        this.geminiApiClient = geminiApiClient;
        this.chatService = chatService;
    }

    @GetMapping("/chat")
    public String chatPage(Model model) {
        model.addAttribute("chatHistory", chatHistory);
        return "/api/chat"; // chat.mustache 뷰 이름
    }

    @PostMapping("/chat")
    @ResponseBody
    public Map<String, Object> handleChatMessage(@RequestParam String message) {
        Map<String, Object> response = new HashMap<>();
        chatHistory.add(Map.of("sender", "user", "message", message));
        try {
            System.out.println("Gemini API 호출 시작: " + message); // 로그 추가
            Thread.sleep(2000); // 잠시 대기 (테스트 목적)
            String geminiResponse = geminiApiClient.generateText(message);
            geminiResponse = geminiResponse.replaceAll("\n", "<br>");
            System.out.println("Gemini API 호출 완료: " + geminiResponse); // 로그 추가
            chatHistory.add(Map.of("sender", "bot", "message", geminiResponse));
            response.put("response", geminiResponse);
        } catch (Exception e) {
            response.put("error", "Gemini API 호출 오류: " + e.getMessage());
        }
        return response;
    }
    @PostMapping("/chat/recipe")
    public String recommendRecipe(@RequestParam("ingredients") String ingredients, Model model){
        String geminiResponse = chatService.recommendRecipePrompt(ingredients);
        System.out.println("Gemini API 호출 완료: " + geminiResponse);

        List<Map<String, String>> chatHistory = new ArrayList<>();

        chatHistory.add(Map.of("sender","user","message",ingredients));
        geminiResponse = geminiResponse.replaceAll("\n", "<br>");
        chatHistory.add(Map.of("sender", "bot", "message", geminiResponse));

        model.addAttribute("chatHistory", chatHistory);



        return "/api/chat";
    }
}



