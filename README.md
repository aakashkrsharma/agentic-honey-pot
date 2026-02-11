# Agentic Honey-Pot for Scam Detection & Intelligence Extraction

## Overview
This project implements an AI-inspired agentic honeypot system that detects scam messages and autonomously engages scammers to extract actionable intelligence such as UPI IDs, bank account details, and phishing URLs.

The system maintains conversation state across multiple turns and avoids revealing scam detection to the scammer.

---

## 🚀 Live Demo

You can try the honeypot simulation here:

👉 **Frontend App:**  
https://aakashkrsharma.github.io/agentic-honey-pot-fe/

This interactive UI allows users to simulate scam conversations and observe:
- Scam detection behavior
- Agent engagement strategy
- Extracted intelligence (UPI IDs, bank accounts, phishing URLs)

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

## Features

- Multi-turn conversation tracking
- Sticky scam mode activation
- Keyword-based scam scoring system
- Regex-based intelligence extraction
- Dynamic agent reply generation
- RESTful architecture

---

## Security & CORS

- CORS enabled for frontend integration
- Designed for demo and educational use
- Not intended for production deployment without authentication and rate limiting


---

## Deployment
- Deployed as a public REST API 
- Compatible with automated honeypot testing tools

---

## Run Locally

1. Clone the repository
2. Configure application.properties if needed
3. Run:

mvn spring-boot:run

The API will start at:
http://localhost:8080

---

### Health Check

GET /health

Response:
"OK"

---

## Limitations

- Uses keyword-based detection (not ML-based)
- In-memory conversation storage (resets on restart)
- Not optimized for high concurrency

---

## Future Enhancements

- Replace keyword-based detection with ML/NLP model
- Persist conversations in a database instead of in-memory storage
- Add rate limiting and authentication for production hardening
- Add dashboard UI to visualize extracted scam intelligence

---

## Architecture Diagram
````
       ┌──────────────────────────────┐
       │      React Frontend          │
       │  (GitHub Pages Deployment)   │
       └───────────────┬──────────────┘
                       │
                       │ HTTP (REST API)
                       ▼
       ┌──────────────────────────────┐
       │     Spring Boot Backend      │
       │  /api/honeypot/message       │
       └───────────────┬──────────────┘
                       │
       ┌───────────────┼────────────────────────┐
       ▼               ▼                        ▼
┌────────────────┐ ┌────────────────┐ ┌──────────────────┐
│ Scam Detector  │ │ Conversation   │ │ Intelligence     │
│ (Keyword +     │ │ State Manager  │ │ Extractor        │
│ Scoring Logic) │ │ (In-Memory)    │ │ (Regex Parsing)  │
└────────────────┘ └────────────────┘ └──────────────────┘
        │                  │                        │
        └──────────┬───────┴─────────────┬──────────┘
                   ▼                     ▼
            ┌──────────────────────────────┐
            │     JSON Response Builder    │
            │ scamDetected, reply, intel   │
            └──────────────────────────────┘
