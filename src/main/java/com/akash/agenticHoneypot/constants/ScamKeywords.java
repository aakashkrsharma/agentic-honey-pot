package com.akash.agenticHoneypot.constants;

import java.util.List;

public class ScamKeywords {
    public static final List<String> SCAM_KEYWORDS = List.of(
            // urgency & pressure
            "urgent",
            "immediately",
            "immediate",
            "quickly",
            "hurry",
            "time is running out",
            "time is critical",
            "time-sensitive",
            "today",

            // account fear
            "account is blocked",
            "compromised",
            "account security",
            "account suspension",
            "suspicious transaction",
            "fraudulent transaction",
            "verify",
            "bank account",
            "blocked",

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
}
