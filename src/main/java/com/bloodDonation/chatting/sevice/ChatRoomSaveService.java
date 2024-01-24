package com.bloodDonation.chatting.sevice;

import com.bloodDonation.chatting.controllers.RequestChatRoom;
import com.bloodDonation.chatting.entities.ChatRoom;
import com.bloodDonation.chatting.repositories.ChatRoomRepository;
import com.bloodDonation.member.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomSaveService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberUtil memberUtil;
    public ChatRoom save(RequestChatRoom form) {
        String roomId = form.getRoomId();

        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseGet(() -> ChatRoom.builder()
                        //.roomId(roomId)
                        .member(memberUtil.getMember())
                        .build());

        room.setRoomNm(form.getRoomNm());
        room.setCapacity(form.getCapacity());

        chatRoomRepository.saveAndFlush(room);

        return room;
    }
}