package com.chu.chat.controller;

import com.chu.chat.dto.ChatMessage;
import com.chu.chat.dto.ChatMessage.MESSAGE_TYPE;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;


@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

  private final SimpMessageSendingOperations messagingTemplate;


  @Autowired
  WebSocketMessageBrokerStats webSocketMessageBrokerStats;

  @MessageMapping("/chat/message")
  public void message( ChatMessage message) {


    log.info(webSocketMessageBrokerStats.getWebSocketSessionStatsInfo());
    log.info(webSocketMessageBrokerStats.getStompBrokerRelayStatsInfo());
    log.info("inbound : {}",webSocketMessageBrokerStats.getClientInboundExecutorStatsInfo());
    log.info("scueduler task  : {} ",webSocketMessageBrokerStats.getSockJsTaskSchedulerStatsInfo());
    log.info("outbound  : {} ",webSocketMessageBrokerStats.getClientOutboundExecutorStatsInfo());

    log.info("ip : {}, message:{}",new Gson().toJson(message));

    if (MESSAGE_TYPE.ENTER.equals(message.getType())) {
      message.setMessage(message.getSender() + "님이 입장하셧습니다.");
      messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    } else {
      messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
  }

}

