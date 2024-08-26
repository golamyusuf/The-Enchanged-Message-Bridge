package com.golamyusuf.demo.adapter;

import com.golamyusuf.demo.dtos.MessageDTO;
import com.golamyusuf.demo.entities.Message;

public class MessageAdapter {
    // Convert MessageDto to Message
    public static MessageDTO mapToDTO(Message message) {
        return MessageDTO.builder()
                .id(message.getId())
                .sender(message.getSender())
                .content(message.getContent())
                .originalFileName(message.getOriginalFileName())
                .contentType(message.getContentType())
                .fileData(message.getFileData())
                .build();
    }
}
