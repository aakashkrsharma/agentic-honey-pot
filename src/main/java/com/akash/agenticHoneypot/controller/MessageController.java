package com.akash.agenticHoneypot.controller;

import com.akash.agenticHoneypot.dto.MessageRequest;
import com.akash.agenticHoneypot.dto.MessageResponse;
import com.akash.agenticHoneypot.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

import static org.springframework.http.MediaType.ALL_VALUE;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {

    @Autowired
    private MessageService messageService;

    private static Logger log = LoggerFactory.getLogger(MessageController.class);

    @PostMapping(value = "/api/honeypot/message", consumes = ALL_VALUE)
    public ResponseEntity<?> respondToMessage(@RequestBody(required = false) MessageRequest messageRequest){

        if(messageRequest == null){
            // Response structure as per hackathon
//            return ResponseEntity.badRequest()
//                    .body(Map.of("status", "error", "reply", "Invalid request"));

            return ResponseEntity.badRequest().body(Map.of("error","Request body is missing or invalid"));
        }

        // Response structure as per hackathon
//        MessageResponse response = messageService.respondToMessage(messageRequest);
//        return ResponseEntity.ok(
//                Map.of(
//                        "status","success",
//                        "reply",response.getReply()
//                )
//        );

        return ResponseEntity.ok(messageService.respondToMessage(messageRequest));
    }

    @GetMapping("/health")
    public ResponseEntity<String> health(){

        log.info("Health check received at {}", Instant.now());
        return ResponseEntity.ok("OK");
    }
}
