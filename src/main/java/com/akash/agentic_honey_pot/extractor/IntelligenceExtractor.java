package com.akash.agentic_honey_pot.extractor;

import com.akash.agentic_honey_pot.dto.MessageResponse;
import com.akash.agentic_honey_pot.model.Conversation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static com.akash.agentic_honey_pot.constants.RegexPatterns.*;
import static com.akash.agentic_honey_pot.constants.RegexPatterns.IFSC_PATTERN;

@Component
public class IntelligenceExtractor {
    public void extract(String message, Conversation conversation){
        if(message == null){
            return;
        }

        Matcher upiMatcher = UPI_PATTERN.matcher(message);
        while (upiMatcher.find()){
            conversation.getUpiIds().add(upiMatcher.group());
        }
        Matcher urlMatcher = URL_PATTERN.matcher(message);
        while (urlMatcher.find()){
            conversation.getPhishingUrls().add(urlMatcher.group());
        }

        Matcher accMatcher = BANK_ACCOUNT_PATTERN.matcher(message);
        Matcher ifscMatcher = IFSC_PATTERN.matcher(message);
        List<String> accounts = new ArrayList<>();
        while (accMatcher.find()){
            accounts.add(accMatcher.group());
        }
        List<String> ifscCodes = new ArrayList<>();
        while (ifscMatcher.find()){
            ifscCodes.add(ifscMatcher.group());
        }
        int size = Math.min(accounts.size(),ifscCodes.size());
        for(int i=0;i<size;i++){
            MessageResponse.BankAccounts bank = new MessageResponse.BankAccounts();
            bank.setAccountNumber(accounts.get(i));
            bank.setIfsc(ifscCodes.get(i));
            conversation.getBankAccounts().add(bank);
        }
    }
}
