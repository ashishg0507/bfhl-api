package com.example.bfhl.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import com.example.bfhl.service.BfhlService;

@RestController
public class BfhlController {

    private final BfhlService service;

    public BfhlController(BfhlService service) {
        this.service = service;
    }

    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
                "is_success", true,
                "official_email", "ashish1630.be23@chitkara.edu.in"
        );
    }

    @PostMapping("/bfhl")
    public ResponseEntity<?> bfhl(@RequestBody Map<String, Object> request) {
        try {
            Object data = service.process(request);
            return ResponseEntity.ok(Map.of(
                    "is_success", true,
                    "official_email", "ashish1630.be23@chitkara.edu.in",
                    "data", data
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "is_success", false
            ));
        }
    }
}
