package com.example.HealthCare.controller;

import com.example.HealthCare.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/ai")
public class AIController {

    @Autowired
    AIService aiService;

    // AI Chatbot endpoint
    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody Map<String, String> request) {
        String question = request.get("question");
        String answer = aiService.chat(question);
        Map<String, String> response = new HashMap<>();
        response.put("answer", answer);
        return response;
    }

    // Doctor Suggester endpoint
    @PostMapping("/suggest-doctor")
    public Map<String, String> suggestDoctor(@RequestBody Map<String, String> request) {
        String disease = request.get("disease");
        String suggestion = aiService.suggestDoctor(disease);
        Map<String, String> response = new HashMap<>();
        response.put("suggestion", suggestion);
        return response;
    }

    // Report Summarizer endpoint
    @PostMapping("/summarize")
    public Map<String, String> summarize(@RequestBody Map<String, String> request) {
        String details = request.get("details");
        String summary = aiService.summarizeReport(details);
        Map<String, String> response = new HashMap<>();
        response.put("summary", summary);
        return response;
    }
}
