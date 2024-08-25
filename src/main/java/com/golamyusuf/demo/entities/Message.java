package com.golamyusuf.demo.entities;

import jakarta.persistence.*;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/*@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "message")*/
/*import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.web.multipart.MultipartFile;
import lombok.*;*/

public class Message {

    private String sender;
    private String content;
    private byte[] fileData; // File data as byte array
    private String originalFileName;
    private String contentType;

    // Constructors, getters, and setters

    public Message() {
    }

    public Message(String sender, String content, MultipartFile file) throws IOException {
        this.sender = sender;
        this.content = content;
        System.out.println("45 File "+file);
        if (file != null && !file.isEmpty()) {
            this.fileData = file.getBytes();
            this.originalFileName = file.getOriginalFilename();
            this.contentType = file.getContentType();
        }
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    // Utility method to convert file data to MultipartFile (for consuming side)
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
                return fileData.length;
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


