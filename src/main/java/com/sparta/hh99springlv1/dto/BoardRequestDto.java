package com.sparta.hh99springlv1.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardRequestDto {
    private String title;
    private String username;
    private String password;
    private String contents;
}
