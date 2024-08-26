package com.golamyusuf.demo.adapter;

import com.golamyusuf.demo.dtos.MessageDTO;
import com.golamyusuf.demo.dtos.MessageRequest;
import com.golamyusuf.demo.dtos.MessageResponse;
import com.golamyusuf.demo.entities.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Component
public class MessageAdapter {
    @Autowired
    Environment environment;

    // Convert MessageDto to Message
    public static MessageDTO mapToDTO(Message message) {
        return MessageDTO.builder()
                .id(message.getId())
                .sender(message.getSender())
                .content(message.getContent())
                .originalFileName(message.getOriginalFileName())
                .contentType(message.getContentType())
                .filePath(message.getFilePath())
                .build();
    }

    // Convert MessageRequest DTO to Message entity
    public Message toEntity(MessageRequest dto) throws IOException {
        return Message.builder()
                .id(dto.getId())
                .sender(dto.getSender())
                .content(dto.getContent())
                .originalFileName(dto.getOriginalFileName())
                .contentType(dto.getContentType())
                .filePath(dto.getOriginalFileName() != null ? saveFile(dto) : null)
                .build();
    }

    // Convert Message entity to MessageRequest DTO
    public MessageRequest toDto(Message entity) throws IOException {
        MultipartFile file = entity.toMultipartFile();
        return MessageRequest.builder()
                .id(entity.getId())
                .sender(entity.getSender())
                .content(entity.getContent())
                .fileData(file != null ? file.getBytes() : null)
                .originalFileName(entity.getOriginalFileName())
                .contentType(entity.getContentType())
                .build();
    }

    // Helper method to save file from MessageRequest DTO
    private String saveFile(MessageRequest dto) throws IOException {
        String directory =  environment.getProperty("file.upload-dir");
        System.out.println(" 58 uploadedDir "+directory);
        Path filePath = Files.createDirectories(Paths.get(directory)).resolve(dto.getOriginalFileName());
        Files.write(filePath, dto.getFileData());
        return filePath.toString();
    }

    public static MessageResponse mapToResponse(Message message)   {
        byte[] fileData = null;
        try {
            if (Optional.ofNullable(message.getFilePath()).isPresent())
                fileData = Files.readAllBytes(Paths.get(message.getFilePath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return MessageResponse.builder()
                .id(message.getId())
                .sender(message.getSender())
                .content(message.getContent())
                .originalFileName(message.getOriginalFileName())
                .contentType(message.getContentType())
                .fileData(fileData)
                .build();
    }
}
