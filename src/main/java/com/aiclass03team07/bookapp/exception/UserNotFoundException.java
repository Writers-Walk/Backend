package com.aiclass03team07.bookapp.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String userId, String password){super("아이디 또는 비밀번호가 틀렸습니다.");}
}
