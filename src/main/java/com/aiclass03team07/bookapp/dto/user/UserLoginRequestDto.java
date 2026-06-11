package com.aiclass03team07.bookapp.dto.user;


import lombok.Data;

@Data
public class UserLoginRequestDto {
    private String userId;
    private String password;
}
