package com.akash.agenticHoneypot.model;

import com.akash.agenticHoneypot.dto.MessageResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class Conversation {
    private int totalTurns;
    private int scamTurns;
    private boolean scamDetected;

    private Set<String> upiIds = new HashSet<>();
    private Set<String> phishingUrls = new HashSet<>();
    private List<MessageResponse.BankAccounts> bankAccounts = new ArrayList<>();
}
