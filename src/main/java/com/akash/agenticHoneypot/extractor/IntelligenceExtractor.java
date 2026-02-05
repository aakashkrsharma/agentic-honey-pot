package com.akash.agenticHoneypot.extractor;

import com.akash.agenticHoneypot.dto.MessageResponse;
import com.akash.agenticHoneypot.model.Conversation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import static com.akash.agenticHoneypot.constants.RegexPatterns.*;
import static com.akash.agenticHoneypot.constants.RegexPatterns.IFSC_PATTERN;

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

        Set<String> accounts = new HashSet<>();
        while (accMatcher.find()){
            accounts.add(accMatcher.group());
        }
        Set<String> ifscCodes = new HashSet<>();
        while (ifscMatcher.find()){
            ifscCodes.add(ifscMatcher.group());
        }

        List<String> accountList = new ArrayList<>(accounts);
        List<String> ifscCodeList = new ArrayList<>(ifscCodes);

        for(int i=0;i<accountList.size();i++){
            MessageResponse.BankAccounts bank = new MessageResponse.BankAccounts();
            bank.setAccountNumber(accountList.get(i));
            if(i < ifscCodeList.size()){
                bank.setIfsc(ifscCodeList.get(i));
            }else{
                bank.setIfsc(null);
            }
            Set<String> existingAccounts = conversation.getBankAccounts()
                            .stream()
                                    .map(MessageResponse.BankAccounts::getAccountNumber)
                                            .collect(Collectors.toSet());
            if(!existingAccounts.contains(accountList.get(i))){
                conversation.getBankAccounts().add(bank);
            }
        }
    }
}