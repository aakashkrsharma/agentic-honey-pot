package com.akash.agentic_honey_pot.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class MessageRequest {

    private String sessionId;
    private MessagePayload message;
//    private List<Object> conversationHistory;
    private Metadata metadata;

    @Getter
    @Setter
    public static class MessagePayload {
        private String sender;
        private String text;
        private long timestamp;
    }

    @Getter
    @Setter
    public static class Metadata {
        private String channel;
        private String language;
        private String locale;
    }
}