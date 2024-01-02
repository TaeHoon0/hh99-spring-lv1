package com.sparta.hh99springlv1.service;

import com.sparta.hh99springlv1.dto.BoardRequestDto;
import com.sparta.hh99springlv1.dto.BoardResponseDto;
import com.sparta.hh99springlv1.entity.Board;
import com.sparta.hh99springlv1.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final JdbcTemplate jdbcTemplate;
    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(JdbcTemplate jdbcTemplate, BoardRepository boardRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.boardRepository = boardRepository;
    }

    public BoardResponseDto createBoard(BoardRequestDto requestDto) {
        // RequestDto -> Entity
        Board board = new Board(requestDto);

        // DB 저장
        BoardRepository boardRepository = new BoardRepository(jdbcTemplate);
        Board savedBoard = boardRepository.create(board);

        // Entity -> ResponseDto
        return new BoardResponseDto(savedBoard);
    }

    public List<BoardResponseDto> getBoards() {
        // DB 조회
        BoardRepository boardRepository = new BoardRepository(jdbcTemplate);
        return boardRepository.findAll();
    }

    public BoardResponseDto getBoard(Long id){
        BoardRepository boardRepository = new BoardRepository(jdbcTemplate);
        return boardRepository.findBoard(id);
    }

    public Long updateBoard(Long id, BoardRequestDto requestDto) {
        BoardRepository boardRepository = new BoardRepository(jdbcTemplate);
        // 해당 게시물이 DB에 존재하는지 확인
        Board board = boardRepository.findById(id);
        if (board != null) {
            // board 내용 수정
            boardRepository.update(id, requestDto);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 게시물은 존재하지 않습니다.");
        }
    }

    public Long deleteBoard(Long id) {
        BoardRepository boardRepository = new BoardRepository(jdbcTemplate);
        // 해당 게시물이 DB에 존재하는지 확인
        Board board = boardRepository.findById(id);
        if (board != null) {
            // board 삭제
            boardRepository.delete(id);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 게시물은 존재하지 않습니다.");
        }
    }
}
