package com.akash.agenticHoneypot.dto;

import lombok.Getter;
import lombok.Setter;

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