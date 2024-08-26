package com.golamyusuf.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDTO {

    private Long id;
    private String sender;
    private String content;
    private String originalFileName;
    private String contentType;
    private byte[] fileData;
}