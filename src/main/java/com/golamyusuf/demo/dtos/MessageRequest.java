package com.golamyusuf.demo.dtos;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MessageRequest {

    private String sender;
    private String content;
    private MultipartFile file;
}
