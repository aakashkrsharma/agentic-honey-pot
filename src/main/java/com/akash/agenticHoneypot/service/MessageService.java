package com.akash.agenticHoneypot.service;

import com.akash.agenticHoneypot.extractor.IntelligenceExtractor;
import com.akash.agenticHoneypot.model.Conversation;
import com.akash.agenticHoneypot.dto.MessageRequest;
import com.akash.agenticHoneypot.dto.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.akash.agenticHoneypot.constants.ScamKeywords.SCAM_KEYWORDS;

@Service
public class MessageService {

    private final IntelligenceExtractor intelligenceExtractor;

    private final Map<String, Conversation> conversations = new ConcurrentHashMap<>();

    private static final Logger log = LoggerFactory.getLogger(MessageService.class);

    public MessageService(IntelligenceExtractor intelligenceExtractor){
        this.intelligenceExtractor = intelligenceExtractor;
    }

    public MessageResponse respondToMessage(MessageRequest request) {

        // Exit early if sessionId is missing (required to track conversation state)
        if (request.getSessionId() == null) {
            throw new IllegalArgumentException("sessionId is required");
        }

        // Get previous conversation if none exist create new conversation
        Conversation conversation = conversations.computeIfAbsent(
                request.getSessionId(),
                id -> new Conversation()
        );

        conversation.setTotalTurns(conversation.getTotalTurns() + 1);

        // Prepare API response object
        MessageResponse response = new MessageResponse();

        if (request.getMessage() == null || request.getMessage().getText() == null) {
            throw new IllegalArgumentException("message.text is required");
        }

        String text = request.getMessage().getText();
        boolean scamDetected = conversation.isScamDetected() || isScamMessage(text);
        conversation.setScamDetected(scamDetected);

        // Update message turns in conversation and extract upi id, bank details, phishing URL if this is scam message
        if(scamDetected){
            conversation.setScamTurns(conversation.getScamTurns() + 1);
            intelligenceExtractor.extract(text, conversation);
        }

        response.setScamDetected(scamDetected);
        response.setAgentActive(scamDetected);
        response.setReply(generateReply(conversation));

        // Populate engagement metrics in response
        MessageResponse.EngagementMetrics metrics = new MessageResponse.EngagementMetrics();
        metrics.setConversationTurns(conversation.getTotalTurns());

        response.setEngagementMetrics(metrics);

        // Update extracted upi id, bank details, phishing URL in the response
        MessageResponse.ExtractedIntelligence intel = new MessageResponse.ExtractedIntelligence();
        intel.setUpiIds(new ArrayList<>(conversation.getUpiIds()));
        intel.setBankAccounts(conversation.getBankAccounts());
        intel.setPhishingUrls(new ArrayList<>(conversation.getPhishingUrls()));

        response.setExtractedIntelligence(intel);

        log.info("Session {} | turn {} | scamDetected {}", request.getSessionId(), conversation.getTotalTurns(), scamDetected);

        return response;
    }

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

    public String generateReply(Conversation conversation) {
        if (!conversation.isScamDetected()) {
            return "Hello! How can I help you?";
        }

        if (conversation.getScamTurns() == 1) {
            return "I’m not sure, can you explain more?";
        } else if (conversation.getScamTurns() == 2) {
            return "Please share the details to proceed.";
        } else {
            return "I am facing issues, can you resend the info?";
        }
    }
}