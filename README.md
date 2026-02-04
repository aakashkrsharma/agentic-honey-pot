# Agentic Honey-Pot for Scam Detection & Intelligence Extraction

## Overview
This project implements an AI-inspired agentic honeypot system that detects scam messages and autonomously engages scammers to extract actionable intelligence such as UPI IDs, bank account details, and phishing URLs.

The system maintains conversation state across multiple turns and avoids revealing scam detection to the scammer.

---

## Architecture
- **Spring Boot REST API**
- **Stateful conversation tracking** using in-memory storage
- **Sticky scam detection** once scam intent is identified
- **Agent-based response generation**
- **Regex-based intelligence extraction**

---

## API Contract

### Endpoint

POST /api/honeypot/message
### Headers
x-api-key: <your-api-key>

### Request Body
```json
{
  "sessionId": "unique-session-id",
  "message": {
    "sender": "scammer",
    "text": "Urgent: Your account is blocked. Share UPI ID.",
    "timestamp": 1770185392437
  }
}
```
### Response Body
```json
{
  "scamDetected": true,
  "agentActive": true,
  "reply": "I’m not sure, can you explain more?",
  "engagementMetrics": {
    "conversationTurns": 2
  },
  "extractedIntelligence": {
    "upiIds": [],
    "bankAccounts": [],
    "phishingUrls": []
  }
}
```

--- 

## Key Design Decisions

- **Sticky Scam Detection**  
  Once scam intent is detected, the conversation remains in scam mode for all subsequent turns.

- **Conversation Turns**  
  `conversationTurns` represents the total number of interaction turns for the session.

- **Agent Behavior**  
  Responses are intentionally vague and human-like to encourage scammers to reveal more information.

---

## Security
- API secured using x-api-key header
- Unauthorized requests return HTTP 401

---

## Deployment
- Deployed as a public REST API 
- Compatible with automated honeypot testing tools

---