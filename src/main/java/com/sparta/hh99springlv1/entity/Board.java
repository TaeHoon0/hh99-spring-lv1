package com.sparta.hh99springlv1.entity;

import com.sparta.hh99springlv1.dto.BoardRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    private Long id;
    private String title;
    private String username;
    private String password;
    private String contents;
    private LocalDateTime createDate;

    public Board(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.contents = requestDto.getContents();
    }
}
