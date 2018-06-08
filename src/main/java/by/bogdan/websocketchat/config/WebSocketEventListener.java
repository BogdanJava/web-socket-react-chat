package by.bogdan.websocketchat.config;

import by.bogdan.websocketchat.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import static by.bogdan.websocketchat.model.ChatMessage.MessageType.LEAVE;

/**
 * @author Bogdan Shishkin
 * project: web-socket-chat
 * date/time: 09.06.2018 / 0:02
 * email: bogdanshishkin1998@gmail.com
 */

@Service
public class WebSocketEventListener {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations sendingOperations;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            logger.info("User disconnected: " + username);
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMessageType(LEAVE);
            chatMessage.setSender(username);

            this.sendingOperations.convertAndSend("/topic/public", chatMessage);
        }
    }


}
