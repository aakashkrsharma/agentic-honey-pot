package com.akash.agentic_honey_pot.controller;

import com.akash.agentic_honey_pot.dto.MessageRequest;
import com.akash.agentic_honey_pot.dto.MessageResponse;
import com.akash.agentic_honey_pot.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping(value = "/api/honeypot/message", consumes = ALL_VALUE)
    public ResponseEntity<?> respondToMessage(@RequestBody(required = false) MessageRequest messageRequest){

        if(messageRequest == null){
            return ResponseEntity.badRequest().body(Map.of("error","Request body is missing or invalid"));
        }
        return ResponseEntity.ok(messageService.respondToMessage(messageRequest));
    }
}
