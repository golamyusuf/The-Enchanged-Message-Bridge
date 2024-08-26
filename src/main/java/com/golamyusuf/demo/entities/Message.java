package com.golamyusuf.demo.entities;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "messages")
public class Message extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sender;
    private String content;
    private String filePath;
    private String originalFileName;
    private String contentType;

    public Message(String sender, String content, MultipartFile file) throws IOException {
        this.sender = sender;
        this.content = content;
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            this.filePath = saveFile(file); // Save the file and get the file path
            this.originalFileName = fileName;
            this.contentType = file.getContentType();
        }
    }

    private String saveFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String directory = "uploaded-files"; // Directory for storing files

        // Ensure the directory exists
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        Path filePath = Paths.get(directory, fileName);
        Files.write(filePath, file.getBytes());

        return filePath.toString(); // Return the relative file path
    }

    // Convert to MultipartFile
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
                return filePath == null || filePath.isEmpty();
            }

            @Override
            public long getSize() {
                File file = new File(filePath);
                return file.length();
            }

            @Override
            public byte[] getBytes() throws IOException {
                return Files.readAllBytes(Paths.get(filePath));
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return Files.newInputStream(Paths.get(filePath));
            }

            @Override
            public Resource getResource() {
                return new FileSystemResource(filePath);
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                Files.copy(Paths.get(filePath), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        };
    }
}