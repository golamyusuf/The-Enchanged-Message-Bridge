package com.golamyusuf.demo;

import com.golamyusuf.demo.dtos.MessageDto;
import com.golamyusuf.demo.dtos.MessageRequest;

public class MessageAdapter {
    // Convert MessageDto to MessageRequest
    public static MessageRequest dtoToRequest(MessageDto dto) {
        if (dto == null) {
            return null;
        }

        MessageRequest request = new MessageRequest();
        request.setId(dto.getId() != null ? dto.getId() : 0);
        request.setSender(dto.getSender());
        request.setContent(dto.getContent());
        request.setUploadedFileName(dto.getUploadedFileName());

        return request;
    }

    // Convert MessageRequest to MessageDto
    public static MessageDto requestToDto(MessageRequest request) {
        if (request == null) {
            return null;
        }

        MessageDto dto = new MessageDto();
        dto.setId(request.getId());
        dto.setSender(request.getSender());
        dto.setContent(request.getContent());
        dto.setUploadedFileName(request.getUploadedFileName());

        return dto;
    }
}
