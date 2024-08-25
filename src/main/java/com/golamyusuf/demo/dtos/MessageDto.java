package com.golamyusuf.demo.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class MessageDto {
    private Long id;
    private String sender;
    private String content;
    private MultipartFile uploadedFileName;
}