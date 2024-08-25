package com.golamyusuf.demo.utils;

import com.golamyusuf.demo.dtos.MessageRequest;
import org.springframework.web.multipart.MultipartFile;

public class DocumentUploadUtils {
    public static MessageRequest getMessageRequestData(String sender, String content, MultipartFile uploadedFileName){
        MessageRequest messageRequest1 = new MessageRequest();
        return messageRequest1.builder().sender(sender)
                .content(content)
                .uploadedFileName(uploadedFileName).build();
    }
}
