package com.akash.agentic_honey_pot;

import com.akash.agentic_honey_pot.agent.AgentResponder;
import com.akash.agentic_honey_pot.detector.ScamDetector;
import com.akash.agentic_honey_pot.dto.MessageRequest;
import com.akash.agentic_honey_pot.dto.MessageResponse;
import com.akash.agentic_honey_pot.extractor.IntelligenceExtractor;
import com.akash.agentic_honey_pot.model.Conversation;
import com.akash.agentic_honey_pot.service.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private ScamDetector scamDetector;

    @Mock
    private AgentResponder agentResponder;

    @Mock
    private IntelligenceExtractor intelligenceExtractor;

    @InjectMocks
    private MessageService messageService;

    @Test
    void shouldDetectScamOnFirstMessage() {
        MessageRequest.MessagePayload payload = new MessageRequest.MessagePayload();
        payload.setSender("scammer");
        payload.setText("Your account is blocked, verify immediately");
        payload.setTimestamp(System.currentTimeMillis());

        MessageRequest request = new MessageRequest();
        request.setSessionId("test-session-01");
        request.setMessage(payload);

        when(scamDetector.isScamMessage(anyString())).thenReturn(true);
        when(agentResponder.generateReply(any())).thenReturn("Please share details");

        MessageResponse response = messageService.respondToMessage(request);

        assertTrue(response.isScamDetected());
        assertTrue(response.isAgentActive());
        assertEquals(1, response.getEngagementMetrics().getConversationTurns());
    }

    @Test
    void shouldPersistScamAcrossMessagesOnceDetected() {
        MessageRequest.MessagePayload payload1 = new MessageRequest.MessagePayload();
        payload1.setSender("scammer");
        payload1.setText("Your account is blocked, verify immediately");
        payload1.setTimestamp(System.currentTimeMillis());

        MessageRequest first = new MessageRequest();
        first.setSessionId("test-session-02");
        first.setMessage(payload1);

        MessageRequest.MessagePayload payload2 = new MessageRequest.MessagePayload();
        payload2.setSender("scammer");
        payload2.setText("please send details");
        payload2.setTimestamp(System.currentTimeMillis());

        MessageRequest second = new MessageRequest();
        second.setSessionId("test-session-02");
        second.setMessage(payload2);

        when(scamDetector.isScamMessage(anyString()))
                .thenReturn(true)   // first message
                .thenReturn(false); // second message

        when(agentResponder.generateReply(any())).thenReturn("reply");

        MessageResponse r1 = messageService.respondToMessage(first);
        MessageResponse r2 = messageService.respondToMessage(second);

        assertTrue(r1.isScamDetected());
        assertTrue(r2.isScamDetected()); // 🔥 critical assertion
        assertEquals(2, r2.getEngagementMetrics().getConversationTurns());
    }

    @Test
    void shouldExtractIntelligenceWhenScamDetected() {
        MessageRequest.MessagePayload payload = new MessageRequest.MessagePayload();
        payload.setSender("scammer");
        payload.setText("Pay to test@upi");
        payload.setTimestamp(System.currentTimeMillis());

        MessageRequest request = new MessageRequest();
        request.setSessionId("test-session-03");
        request.setMessage(payload);

        when(scamDetector.isScamMessage(anyString())).thenReturn(true);
        when(agentResponder.generateReply(any())).thenReturn("ok");

        messageService.respondToMessage(request);

        verify(intelligenceExtractor, times(1))
                .extract(anyString(), any(Conversation.class));
    }
}