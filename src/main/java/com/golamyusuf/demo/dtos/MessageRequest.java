package com.golamyusuf.demo.dtos;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    private long id;
    private String sender;
    private String content;
    private MultipartFile uploadedFileName;
}
