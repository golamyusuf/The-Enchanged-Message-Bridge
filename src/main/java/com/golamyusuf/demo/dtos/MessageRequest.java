package com.golamyusuf.demo.dtos;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageRequest  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sender;
    private String content;
    @Lob
    private byte[] fileData;
    private String originalFileName;
    private String contentType;

    public MessageRequest(String sender, String content, MultipartFile file) throws IOException {
        this.sender = sender;
        this.content = content;
        if (file != null && !file.isEmpty()) {
            this.fileData = file.getBytes();
            this.originalFileName = file.getOriginalFilename();
            this.contentType = file.getContentType();
        }
    }

    public MultipartFile toMultipartFile() {
        return new MultipartFile() {
            @Override
            public String getName() {
                return originalFileName;
            }

            @Override
            public String getOriginalFilename() {
                return originalFileName;
            }

            @Override
            public String getContentType() {
                return contentType;
            }

            @Override
            public boolean isEmpty() {
                return fileData == null || fileData.length == 0;
            }

            @Override
            public long getSize() {
                return Optional.ofNullable(fileData).isPresent() ? fileData.length : 0;
            }

            @Override
            public byte[] getBytes() {
                return fileData;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(fileData);
            }

            @Override
            public Resource getResource() {
                return MultipartFile.super.getResource();
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                Path path = dest.toPath();
                Files.write(path, fileData);
            }
        };
    }
}


