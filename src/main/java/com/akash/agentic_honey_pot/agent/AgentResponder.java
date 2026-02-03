package com.akash.agentic_honey_pot.agent;

import com.akash.agentic_honey_pot.model.Conversation;
import org.springframework.stereotype.Component;

@Component
public class AgentResponder {

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
