package com.golamyusuf.demo.services;

import com.golamyusuf.demo.adapter.MessageAdapter;
import com.golamyusuf.demo.dtos.MessageDTO;
import com.golamyusuf.demo.dtos.MessageResponse;
import com.golamyusuf.demo.entities.Message;
import com.golamyusuf.demo.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public MessageDTO saveMessage(Message message) {

        Message savedMessage = messageRepository.save(message);

        return MessageAdapter.mapToDTO(savedMessage);
    }

    public List<MessageResponse> getAllMessages() throws IOException {
        System.out.println("34 MessageService ");
        List<MessageResponse> collect = null;
        try {
             collect = messageRepository.findAll().stream()
                    .map(message -> {

                        return MessageAdapter.mapToResponse(message);

                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
          return collect;
    }

    public byte[] getFile(Long messageId) throws IOException {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        Path filePath = Paths.get(message.getFilePath());
        return Files.readAllBytes(filePath);
    }
}
