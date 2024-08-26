package com.golamyusuf.demo.dtos;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String kingdom;
}
