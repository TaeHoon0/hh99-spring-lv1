package com.sparta.hh99springlv1.repository;

import com.sparta.hh99springlv1.dto.BoardRequestDto;
import com.sparta.hh99springlv1.dto.BoardResponseDto;
import com.sparta.hh99springlv1.entity.Board;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class BoardRepository {
    private final JdbcTemplate jdbcTemplate;

    public BoardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Board create(Board board) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO board(title, username, password, contents) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, board.getTitle());
            ps.setString(2, board.getUsername());
            ps.setString(3, board.getPassword());
            ps.setString(4, board.getContents());

            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        board.setId(id);

        return board;
    }


    public List<BoardResponseDto> findAll() {
        String sql = "SELECT * FROM board";

        return jdbcTemplate.query(sql, new RowMapper<BoardResponseDto>() {
            @Override
            public BoardResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {

                // SQL 의 결과로 받아온 Memo 데이터들을 BoardResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String username = rs.getString("username");
                String title = rs.getString("title");
                String contents = rs.getString("contents");

                Timestamp timestamp = rs.getTimestamp("createDate");
                LocalDateTime createDate = timestamp.toLocalDateTime();

                return new BoardResponseDto(id, title, username, contents, createDate);
            }
        });

    }

    public BoardResponseDto findBoard(Long id) {
        String sql = "SELECT * FROM board WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                BoardResponseDto boardResponseDto = new BoardResponseDto();
                boardResponseDto.setId(resultSet.getLong("id"));
                boardResponseDto.setTitle(resultSet.getString("title"));
                boardResponseDto.setUsername(resultSet.getString("username"));
                boardResponseDto.setContents(resultSet.getString("contents"));
                boardResponseDto.setCreateDate(resultSet.getTimestamp("createDate").toLocalDateTime());
                return boardResponseDto;
            } else {
                return null;
            }
        }, id);
    }

    public Board findById(Long id) {
        String sql = "SELECT * FROM board WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Board board = new Board();
                board.setId(resultSet.getLong("id"));
                board.setTitle(resultSet.getString("title"));
                board.setUsername(resultSet.getString("username"));
                board.setContents(resultSet.getString("contents"));
                board.setCreateDate(resultSet.getTimestamp("createDate").toLocalDateTime());

                return board;
            } else {
                return null;
            }
        }, id);
    }

    public void update(Long id, BoardRequestDto requestDto) {
        String sql = "UPDATE board SET username = ?, contents = ?, title = ? WHERE id = ?";
        jdbcTemplate.update(sql, requestDto.getUsername(), requestDto.getContents(), requestDto.getTitle(), id);
    }


    public void delete(Long id) {
        String sql = "DELETE FROM board WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean checkPassword(Long id, String inputPwd) {
        String sql = "SELECT password FROM board WHERE id = ?";

        String pwd =  jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Board board = new Board();
                board.setPassword(resultSet.getString("password"));
                return board.getPassword();
            } else {
                return null;
            }
        }, id);

        return inputPwd.equals(pwd);
    }
}
