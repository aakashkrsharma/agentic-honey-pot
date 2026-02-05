package com.akash.agentic_honey_pot.constants;

import java.util.regex.Pattern;

public class RegexPatterns {
    public static final Pattern UPI_PATTERN =
            Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z]+");
    public static final Pattern URL_PATTERN =
            Pattern.compile("https?://\\S+");
    public static final Pattern BANK_ACCOUNT_PATTERN =
            Pattern.compile("\\b\\d{11,18}\\b");
    public static final Pattern IFSC_PATTERN =
            Pattern.compile("\\b[A-Z]{4}0[A-Z0-9]{6}\\b");
}
