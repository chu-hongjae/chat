package com.chu.chat;

import com.chu.chat.dto.ChatMessage;
import com.chu.chat.dto.ChatRoom;
import com.chu.chat.service.ChatService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketChatHandler extends TextWebSocketHandler {


  private final ChatService chatService;

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    super.handleTextMessage(session, message);
    String payload = message.getPayload();
    log.info("payload : {}", payload);
    ChatMessage chatMessage = new Gson().fromJson(payload, ChatMessage.class);
    ChatRoom chatRoom = chatService.findRoomById(chatMessage.getRoomId());
    chatRoom.handleActions(session, chatMessage, chatService);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    super.afterConnectionClosed(session, status);
    log.info("disconnected : ", session);
  }
}
