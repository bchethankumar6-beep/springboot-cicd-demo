
package com.example.cicddemo;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of(
            "message", "Welcome to Spring Boot CI/CD Project!",
            "project", "GitHub-Jenkins-Docker-Kubernetes",
            "status", "Application is running successfully"
        );
    }

    @GetMapping("/api/hello")
    public Map<String, String> hello() {
        return Map.of("message", "Hello! Your CI/CD pipeline is working.");
    }
}
