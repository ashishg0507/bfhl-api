package com.example.bfhl.service;

import org.springframework.stereotype.Service;
import java.util.*;
import com.example.bfhl.util.MathUtil;

@Service
public class BfhlService {

    private final GeminiAIService geminiAIService;

    public BfhlService(GeminiAIService geminiAIService) {
        this.geminiAIService = geminiAIService;
    }

    public Object process(Map<String, Object> req) {


        if (req.size() != 1) {
            throw new RuntimeException("Request must contain exactly one key");
        }

        // Fibonacci
        if (req.containsKey("fibonacci")) {
            Number n = (Number) req.get("fibonacci");
            return MathUtil.fibonacci(n.intValue());
        }

        // Prime numbers
        if (req.containsKey("prime")) {
            List<?> raw = (List<?>) req.get("prime");
            List<Integer> arr = raw.stream()
                    .map(x -> ((Number) x).intValue())
                    .toList();

            return arr.stream()
                    .filter(MathUtil::isPrime)
                    .toList();
        }

        // LCM
        if (req.containsKey("lcm")) {
            List<?> raw = (List<?>) req.get("lcm");
            List<Integer> arr = raw.stream()
                    .map(x -> ((Number) x).intValue())
                    .toList();

            return MathUtil.lcm(arr);
        }

        // HCF
        if (req.containsKey("hcf")) {
            List<?> raw = (List<?>) req.get("hcf");
            List<Integer> arr = raw.stream()
                    .map(x -> ((Number) x).intValue())
                    .toList();

            return MathUtil.hcf(arr);
        }

        // AI
        if (req.containsKey("AI")) {
            String question = req.get("AI").toString();
            return geminiAIService.ask(question);
        }

        throw new RuntimeException("Invalid request key");
    }
}


