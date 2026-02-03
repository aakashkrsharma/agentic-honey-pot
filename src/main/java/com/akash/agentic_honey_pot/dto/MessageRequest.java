package com.akash.agentic_honey_pot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class MessageRequest {

    @JsonProperty("conversation_id")
    private String conversationId;

    private String message;
}
