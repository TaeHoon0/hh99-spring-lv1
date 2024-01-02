package com.sparta.hh99springlv1.dto;

import com.sparta.hh99springlv1.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String title;
    private String username;
    private String contents;
    private LocalDateTime createDate;

    public BoardResponseDto(Board savedBoard) {
        this.id = savedBoard.getId();
        this.title = savedBoard.getTitle();
        this.username = savedBoard.getUsername();
        this.contents = savedBoard.getContents();
        this.createDate = savedBoard.getCreateDate();
    }

    public BoardResponseDto(Long id, String title, String username, String contents, LocalDateTime createDate) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.contents = contents;
        this.createDate = createDate;
    }

}
