package com.akash.agentic_honey_pot.controller;

import com.akash.agentic_honey_pot.dto.MessageRequest;
import com.akash.agentic_honey_pot.dto.MessageResponse;
import com.akash.agentic_honey_pot.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping(value = "/api/honeypot/message", consumes = APPLICATION_JSON_VALUE)
    public MessageResponse respondToMessage(@RequestBody MessageRequest messageRequest){

        return messageService.respondToMessage(messageRequest);
    }
}
