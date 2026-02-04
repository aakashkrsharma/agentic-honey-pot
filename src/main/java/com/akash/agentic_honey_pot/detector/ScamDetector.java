package com.akash.agentic_honey_pot.detector;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScamDetector {

    private static final List<String> SCAM_KEYWORDS = List.of(
            "account blocked",
            "verify immediately",
            "immediately",
            "kyc",
            "send money",
            "upi",
            "urgent"
    );

    public boolean isScamMessage(String message) {
        if (message == null) return false;

        String lower = message.toLowerCase();
        return SCAM_KEYWORDS.stream().anyMatch(lower::contains);
    }
}
