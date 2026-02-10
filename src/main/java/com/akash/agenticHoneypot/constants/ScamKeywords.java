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
            "act now",
            "limited time",

            // account fear
            "account is blocked",
            "account will be blocked",
            "compromised",
            "account security",
            "account suspension",
            "suspicious transaction",
            "fraudulent transaction",
            "verify",
            "bank account",
            "blocked",
            "account will be upgraded",
            "update your account",

            // credential harvesting
            "share your account number",
            "otp",
            "upi pin",
            "debit card",
            "registered mobile number",

            // compliance / authority
            "kyc",
            "security department",

            // lottery / reward bait
            "you won",
            "lottery",
            "cash prize",
            "prize money",
            "reward amount",
            "lakh",
            "jackpot",

            // payment to claim prize
            "small payment",
            "processing fee",
            "claim your prize",
            "unlock amount",
            "activation fee",
            "pay to withdraw",
            "transfer money",

            // common scam typos / tricks
            "ruppes",
            "ruppess",
            "acount",
            "acct",
            "banking details",
            "upi id",

            // bank name impersonation
            "sbi",
            "icici",
            "hdfc"
    );
}
