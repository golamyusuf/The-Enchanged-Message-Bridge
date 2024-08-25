package com.golamyusuf.demo.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "message")
public class Message extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = true)
    private String content;

    /*@Column(nullable = false)
    private String timestamp;*/

    @Column(name = "uploadedFileName", columnDefinition = "LONGTEXT")
    private String uploadedFileName;
}

