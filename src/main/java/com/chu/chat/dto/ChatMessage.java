package com.chu.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
  public enum MESSAGE_TYPE{
    ENTER,TALK,BROADCAST,WISPER
  }
  private MESSAGE_TYPE type;
  private String roomId;
  private String sender;
  private String message;
}
