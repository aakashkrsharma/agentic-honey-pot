package com.akash.agentic_honey_pot.detector;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class ScamDetector {

    private static final List<String> SCAM_KEYWORDS = List.of(
            // urgency & pressure
            "urgent",
            "immediately",
            "immediate",
            "quickly",
            "hurry",
            "time is running out",
            "time is critical",
            "time-sensitive",

            // account fear
            "account blocked",
            "account compromised",
            "account security",
            "account suspension",
            "suspicious transaction",
            "fraudulent transaction",

            // credential harvesting
            "share your account number",
            "share your otp",
            "last otp",
            "upi pin",
            "debit card",
            "registered mobile number",

            // compliance / authority
            "kyc",
            "security department",
            "verify your identity"
    );

    public boolean isScamMessage(String message) {
        if (message == null) return false;

        String normalized = message.toLowerCase(Locale.ROOT);

        int scamScore = 0;
        for(String keyword : SCAM_KEYWORDS){
            if(normalized.contains(keyword)){
                scamScore++;
            }
        }

        boolean scamDetected = scamScore >= 2;

        return scamDetected;
    }
}
