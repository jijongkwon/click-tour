package com.clicktour.clicktour.service.board;

import com.clicktour.clicktour.domain.board.Board;
import com.clicktour.clicktour.domain.board.Comments;
import com.clicktour.clicktour.domain.board.dto.*;
import com.clicktour.clicktour.domain.users.Users;
import com.clicktour.clicktour.repository.BoardRepository;
import com.clicktour.clicktour.repository.CommentsRepository;
import com.clicktour.clicktour.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UsersRepository usersRepository;
    private final CommentsRepository commentsRepository;

    @Transactional
    public Board saveBoard(BoardSaveRequestDto requestDto) {
        Optional<Users> users = usersRepository.findByNickname(requestDto.getNickname());

        if (users.isEmpty()) {
            return null;
        }

        requestDto.setUsers(users.get());

        return boardRepository.save(requestDto.toEntity());
    }

    @Transactional
    public List<BoardResponseDto> readBoardList() {
        return boardRepository.findAllDesc().
                stream().map(BoardResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public BoardDetailResponseDto readDetailBoard(Long id) {

        Board board = boardRepository.findById(id).orElse(null);

        if (board == null) {
            return null;
        }

        boardRepository.updateView(id);

        return new BoardDetailResponseDto(board);
    }

    @Transactional
    public CommentSaveRequestDto saveComments(Long id, String nickname, CommentSaveRequestDto commentSaveRequestDto) {

        Users users = usersRepository.findByNickname(nickname).orElse(null);
        Board board = boardRepository.findById(id).orElse(null);

        if (board == null) {
            return null;
        }
        commentSaveRequestDto.setBoard(board);
        commentSaveRequestDto.setUsers(users);

        commentsRepository.save(commentSaveRequestDto.toEntity());

        return commentSaveRequestDto;
    }

    @Transactional
    public Board updateBoard(Long id, String nickname, BoardUpdateRequestDto updateRequestDto) {
        Board board = boardRepository.findById(id).orElse(null);

        if (board == null || !board.getUsers().getNickname().equals(nickname)) {
            return null;
        }

        board.update(updateRequestDto.getTitle(), updateRequestDto.getContent());

        return board;
    }

    @Transactional
    public Comments updateComment(Long id, String nickname, CommentUpdateRequestDto commentUpdateRequestDto) {
        Comments comments = commentsRepository.findById(id).orElse(null);

        if (comments == null || !comments.checkUser(nickname)) {
            return null;
        }

        comments.update(commentUpdateRequestDto.getContent());
        return comments;
    }

    @Transactional
    public Board deleteBoard(Long id, String nickname) {
        Board board = boardRepository.findById(id).orElse(null);

        if (board == null || !board.getUsers().getNickname().equals(nickname)) {
            return null;
        }

        boardRepository.delete(board);

        return board;
    }

    @Transactional
    public Comments deleteComment(Long id, String nickname) {
        Comments comments = commentsRepository.findById(id).orElse(null);

        if (comments == null || !comments.checkUser(nickname)) {
            return null;
        }

        commentsRepository.delete(comments);

        return comments;
    }
}
