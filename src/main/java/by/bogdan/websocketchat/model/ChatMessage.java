package by.bogdan.websocketchat.model;

import lombok.Data;

/**
 * @author Bogdan Shishkin
 * project: web-socket-chat
 * date/time: 08.06.2018 / 23:54
 * email: bogdanshishkin1998@gmail.com
 */

@Data
public class ChatMessage {
    private MessageType messageType;
    private String content;
    private String sender;

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
}
