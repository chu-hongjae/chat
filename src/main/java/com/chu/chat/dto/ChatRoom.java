package com.chu.chat.dto;

import com.chu.chat.dto.ChatMessage.MESSAGE_TYPE;
import com.chu.chat.service.ChatService;
import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

@Getter
public class ChatRoom {

  private String roomId;
  private String name;

  private Set<WebSocketSession> sessions = new HashSet<>();

  @Builder
  private ChatRoom(String roomId, String name) {
    this.roomId = roomId;
    this.name = name;
  }

  public void handleActions(WebSocketSession session, ChatMessage chatMessage,
      ChatService service) {
    if (chatMessage.getType().equals(MESSAGE_TYPE.ENTER)) {
      sessions.add(session);
      chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다. ");
    }
    sendMessage(service, chatMessage);
  }

  public <T> void sendMessage(ChatService chatService, T message) {
    sessions.parallelStream().forEach(sessions -> chatService.sendMessage(sessions, message));
  }

}
