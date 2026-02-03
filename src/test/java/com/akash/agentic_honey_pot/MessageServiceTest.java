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
import org.springframework.test.util.ReflectionTestUtils;

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
        MessageRequest request = new MessageRequest();
        ReflectionTestUtils.setField(request, "conversationId", "conv1");
        ReflectionTestUtils.setField(request, "message", "Your account is blocked, verify immediately");

        when(scamDetector.isScamMessage(anyString())).thenReturn(true);
        when(agentResponder.generateReply(any())).thenReturn("Please share details");

        MessageResponse response = messageService.respondToMessage(request);

        assertTrue(response.isScamDetected());
        assertTrue(response.isAgentActive());
        assertEquals(1, response.getEngagementMetrics().getConversationTurns());
    }

    @Test
    void shouldPersistScamAcrossMessages() {
        MessageRequest first = new MessageRequest();
        ReflectionTestUtils.setField(first, "conversationId", "conv2");
        ReflectionTestUtils.setField(first, "message", "urgent kyc update");

        MessageRequest second = new MessageRequest();
        ReflectionTestUtils.setField(second, "conversationId", "conv2");
        ReflectionTestUtils.setField(second, "message", "please send details");

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
        MessageRequest request = new MessageRequest();
        ReflectionTestUtils.setField(request, "conversationId", "conv3");
        ReflectionTestUtils.setField(request, "message", "Pay to test@upi");

        when(scamDetector.isScamMessage(anyString())).thenReturn(true);
        when(agentResponder.generateReply(any())).thenReturn("ok");

        messageService.respondToMessage(request);

        verify(intelligenceExtractor, times(1))
                .extract(anyString(), any(Conversation.class));
    }

}
