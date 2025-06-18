package dev.mayutama.cicdspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final Environment environment;

    @GetMapping
    public ResponseEntity<?> getTest() {
        String[] activeProfiles = environment.getActiveProfiles();
        String activeProfile = "";
        if (activeProfiles.length == 0) {
            activeProfile = "No active profiles set";
        } else {
            activeProfile = String.join(", ", activeProfiles);
        }
        return ResponseEntity.ok("Test application: " + activeProfile);
    }
}
