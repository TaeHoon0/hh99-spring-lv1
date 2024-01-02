package com.sparta.hh99springlv1.controller;

import com.sparta.hh99springlv1.dto.BoardRequestDto;
import com.sparta.hh99springlv1.dto.BoardResponseDto;
import com.sparta.hh99springlv1.service.BoardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
@Tag(name = "Board", description = "Board API")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/boards")
    public ResponseEntity<Object> createBoard(@RequestBody BoardRequestDto requestDto) {

        return handleRequest(() -> boardService.createBoard(requestDto), HttpStatus.CREATED, "게시물 생성에 실패했습니다.");
    }

    @GetMapping("/boards")
    public ResponseEntity<Object> getBoards() {
        return handleRequest(boardService::getBoards, HttpStatus.OK, "게시물 리스트 조회에 실패했습니다.");
    }

    @GetMapping("/boards/{id}")
    public ResponseEntity<Object> getBoard(@PathVariable Long id) {
        return handleRequest(() -> boardService.getBoard(id), HttpStatus.OK, "게시물 조회에 실패했습니다.");
    }

    @PutMapping("/boards/{id}")
    public ResponseEntity<Object> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        return handleRequest(() -> boardService.updateBoard(id, requestDto), HttpStatus.OK, "게시물 수정에 실패했습니다.");
    }

    @DeleteMapping("/boards/{id}")
    public ResponseEntity<Object> deleteBoard(@PathVariable Long id, @RequestBody String password) {
        return handleRequest(() -> boardService.deleteBoard(id, password), HttpStatus.OK, "게시물 삭제에 실패했습니다.");
    }

    private ResponseEntity<Object> handleRequest(ApiRequestHandler handler, HttpStatus successStatus, String errorMessage) {
        try {
            Object result = handler.handle();
            return new ResponseEntity<>(result, successStatus);
        } catch (IllegalArgumentException e) {
            // 클라이언트의 잘못된 요청에 대한 예외 처리
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // 그 외의 예외에 대한 처리
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    interface ApiRequestHandler {
        Object handle() throws Exception;
    }
}
