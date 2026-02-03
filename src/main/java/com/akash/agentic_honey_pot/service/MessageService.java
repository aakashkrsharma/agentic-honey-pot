package com.akash.agentic_honey_pot.service;

import com.akash.agentic_honey_pot.agent.AgentResponder;
import com.akash.agentic_honey_pot.detector.ScamDetector;
import com.akash.agentic_honey_pot.extractor.IntelligenceExtractor;
import com.akash.agentic_honey_pot.model.Conversation;
import com.akash.agentic_honey_pot.dto.MessageRequest;
import com.akash.agentic_honey_pot.dto.MessageResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MessageService {

    private final ScamDetector scamDetector;
    private final AgentResponder agentResponder;
    private final IntelligenceExtractor intelligenceExtractor;

    private final Map<String, Conversation> conversations = new ConcurrentHashMap<>();

    public MessageService(ScamDetector scamDetector, AgentResponder agentResponder, IntelligenceExtractor intelligenceExtractor){
        this.scamDetector = scamDetector;
        this.agentResponder = agentResponder;
        this.intelligenceExtractor = intelligenceExtractor;
    }

    public MessageResponse respondToMessage(MessageRequest request) {

        if (request.getConversationId() == null) {
            throw new IllegalArgumentException("conversation_id is required");
        }

        Conversation conversation = conversations.computeIfAbsent(
                request.getConversationId(),
                id -> new Conversation()
        );

        conversation.setTotalTurns(conversation.getTotalTurns() + 1);

        MessageResponse response = new MessageResponse();

        boolean scamDetected = conversation.isScamDetected() || scamDetector.isScamMessage(request.getMessage());
        conversation.setScamDetected(scamDetected);

        if(scamDetected){
            conversation.setScamTurns(conversation.getScamTurns() + 1);
            intelligenceExtractor.extract(request.getMessage(), conversation);
        }

        response.setScamDetected(scamDetected);
        response.setAgentActive(scamDetected);
        response.setReply(agentResponder.generateReply(conversation));

        MessageResponse.EngagementMetrics metrics = new MessageResponse.EngagementMetrics();
        metrics.setConversationTurns(conversation.getTotalTurns());

        response.setEngagementMetrics(metrics);

        MessageResponse.ExtractedIntelligence intel = new MessageResponse.ExtractedIntelligence();
        intel.setUpiIds(new ArrayList<>(conversation.getUpiIds()));
        intel.setBankAccounts(conversation.getBankAccounts());
        intel.setPhishingUrls(new ArrayList<>(conversation.getPhishingUrls()));

        response.setExtractedIntelligence(intel);

        return response;
    }
}