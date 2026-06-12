package com.aiclass03team07.bookapp.exception;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException() {
        super("아이디 또는 비밀번호가 틀렸습니다.");
    }
}