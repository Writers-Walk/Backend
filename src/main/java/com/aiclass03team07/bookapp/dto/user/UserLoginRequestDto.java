package com.aiclass03team07.bookapp.dto.user;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserLoginRequestDto {
    private String userId;
    private String password;
}
