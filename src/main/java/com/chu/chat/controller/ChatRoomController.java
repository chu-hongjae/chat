package com.chu.chat.controller;

import com.chu.chat.dto.ChatMessage;
import com.chu.chat.dto.ChatMessage.MESSAGE_TYPE;
import com.chu.chat.dto.ChatRoom;
import com.chu.chat.dto.ChatRoomRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

  private final ChatRoomRepository chatRoomRepository;

  /**
   * 채팅 리스트 화면
   * @param model
   * @return
   */
  @GetMapping("/room")
  public String rooms(Model model) {
    return "/chat/room";
  }

  /**
   * 채팅 방 목록 반환
   * @param
   * @return
   */
  @GetMapping("/rooms")
  @ResponseBody
  public List<ChatRoom> room() {
    return chatRoomRepository.findAllRoom();
  }

  /**
   * 채팅방 생성
   * @param name
   * @return
   */
  @PostMapping("/room")
  @ResponseBody
  public ChatRoom createRoom(@RequestParam String name) {
    return chatRoomRepository.createChatRoom(name);
  }

  @GetMapping("/room/enter/{roomId}")
  public String roolDetail(Model model, @PathVariable String roomId) {
    model.addAttribute("roomId", roomId);
    return "/chat/roomDetail";
  }

  @GetMapping("/room/{roomId}")
  @ResponseBody
  public ChatRoom roomInfo(@PathVariable String roomId) {
    return chatRoomRepository.findRoomById(roomId);
  }

}
