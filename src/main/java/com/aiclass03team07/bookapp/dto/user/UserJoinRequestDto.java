package com.aiclass03team07.bookapp.dto.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class UserJoinRequestDto {
    private Long Id;
    private String userId;
    private String password;
    private String role;
    private List<String> genres;
}
