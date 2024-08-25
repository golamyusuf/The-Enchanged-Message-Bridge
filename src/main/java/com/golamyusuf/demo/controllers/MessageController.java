package com.golamyusuf.demo.controllers;

import com.golamyusuf.demo.MessageAdapter;
import com.golamyusuf.demo.dtos.MessageDto;
import com.golamyusuf.demo.dtos.MessageRequest;
import com.golamyusuf.demo.services.KafkaProducer;
import com.golamyusuf.demo.utils.DocumentUploadUtils;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    private KafkaProducer kafkaProducer;

    public MessageController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    //http:localhost:8080/api/v1/message/publish?message=hello world
    @GetMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam("message") String message) {
        kafkaProducer.sendMessage(message);
        return ResponseEntity.ok("Message sent to the topic");
    }

    //localhost:8080/api/v1/message/temp
    @PostMapping("/temp")
    //@PreAuthorize("@authService.authorizeAccess('/carbarn/v1/api/documentupload/upload')")
    public MessageDto sendMessage(
           @RequestParam(value = "sender", required = true) String sender,
           @RequestParam(value = "content", required = false) String content,
           @RequestParam(value = "uploadedFileName", required = false) MultipartFile uploadedFileName
    ) {
        System.out.println(" $$$$$$$$$$$$ 40 sendMessage  " +sender+" content "+content+" uploadedFileName "+uploadedFileName);
        MessageDto document = null;
        try {
            Gson googleJson = new Gson();
            //MessageRequest messageRequest = googleJson.fromJson(messageRequestVal, MessageRequest.class);
            MessageRequest messageRequestVal = DocumentUploadUtils.getMessageRequestData(sender, content, uploadedFileName);
            System.out.println(" 42 messageRequestVal "+messageRequestVal.toString());
            MessageDto messageDto = MessageAdapter.requestToDto(messageRequestVal);
            System.out.println(" 49 messageRequestVal messageDto "+messageDto.toString());
            //document = documentUploadService.persistDocuments(documentUploadDto);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return document;
    }
}
