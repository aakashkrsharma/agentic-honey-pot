package com.akash.agenticHoneypot.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class MessageResponse {
    private boolean scamDetected;
    private boolean agentActive;
    private String reply;
    private EngagementMetrics engagementMetrics;
    private ExtractedIntelligence extractedIntelligence;

    @Getter
    @Setter
    public static class EngagementMetrics{
        private int conversationTurns;
    }

    @Getter
    @Setter
    public static class ExtractedIntelligence {
        private List<String> upiIds;
        private List<BankAccounts> bankAccounts;
        private List<String> phishingUrls;
    }

    @Getter
    @Setter
    public static class BankAccounts{
        private String accountNumber;
        private String ifsc;
    }
}