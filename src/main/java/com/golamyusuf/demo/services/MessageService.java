package com.golamyusuf.demo.services;

import com.golamyusuf.demo.adapter.MessageAdapter;
import com.golamyusuf.demo.dtos.MessageDTO;
import com.golamyusuf.demo.entities.Message;
import com.golamyusuf.demo.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<MessageDTO> getAllMessages() {
        return messageRepository.findAll().stream()
                .map(MessageAdapter::mapToDTO)
                .collect(Collectors.toList());
    }
}
