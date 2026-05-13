package com.example.HealthCare.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class AIService {

    @Value("${groq.api.key}")
    private String apiKey;

    private static final String GROQ_URL =
        "https://api.groq.com/openai/v1/chat/completions";

    private String callGroq(String systemPrompt, String userMessage) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            Map<String, String> systemMsg = new HashMap<>();
            systemMsg.put("role", "system");
            systemMsg.put("content", systemPrompt);

            Map<String, String> userMsg = new HashMap<>();
            userMsg.put("role", "user");
            userMsg.put("content", userMessage);

            Map<String, Object> body = new HashMap<>();
            body.put("model", "llama-3.3-70b-versatile");
            body.put("messages", List.of(systemMsg, userMsg));
            body.put("max_tokens", 500);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(GROQ_URL, entity, Map.class);

            Map responseBody = response.getBody();
            List<Map> choices = (List<Map>) responseBody.get("choices");
            Map message = (Map) choices.get(0).get("message");
            return (String) message.get("content");

        } catch (Exception e) {
            return "Sorry, AI is not available right now. Please try again later.";
        }
    }

    public String chat(String question) {
        String system = "You are a helpful healthcare assistant. " +
            "Answer health questions clearly and simply. " +
            "Always remind users to consult a real doctor. " +
            "Keep answers short and easy to understand.";
        return callGroq(system, question);
    }

    public String suggestDoctor(String disease) {
        String system = "You are a medical specialist recommender. " +
            "Suggest the type of doctor based on disease or symptoms. " +
            "Format your answer as: Doctor Type, Reason, One Tip. " +
            "Keep under 100 words.";
        return callGroq(system, "Patient has: " + disease);
    }

    public String summarizeReport(String details) {
        String system = "You are a healthcare report summarizer. " +
            "Give a brief summary and recommendations. " +
            "Format: Summary, Key Concerns, Recommendations. " +
            "Keep under 150 words.";
        return callGroq(system, "Patient details: " + details);
    }
}
