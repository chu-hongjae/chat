package com.chu.chat.controller;

import com.chu.chat.dto.ChatMessage;
import com.chu.chat.dto.ChatMessage.MESSAGE_TYPE;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;


@RequiredArgsConstructor
@Controller
public class ChatController {

  private final SimpMessageSendingOperations messagingTemplate;

  @MessageMapping("/chat/message")
  public void message(ChatMessage message) {

    if (MESSAGE_TYPE.ENTER.equals(message.getType())) {
      message.setMessage(message.getSender() + "님이 입장하셧습니다.");
      messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    } else {
      messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
  }

}
