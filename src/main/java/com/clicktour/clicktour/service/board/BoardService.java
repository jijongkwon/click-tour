package com.clicktour.clicktour.service.board;

import com.clicktour.clicktour.domain.board.Board;
import com.clicktour.clicktour.domain.board.dto.BoardResponseDto;
import com.clicktour.clicktour.domain.board.dto.BoardSaveRequestDto;
import com.clicktour.clicktour.domain.users.Users;
import com.clicktour.clicktour.repository.BoardRepository;
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

    @Transactional
    public Board save(BoardSaveRequestDto requestDto){
        Optional<Users> users = usersRepository.findByNickname(requestDto.getNickname());

        if(users.isEmpty()){
            return null;
        };

        requestDto.setUsers(users.get());

        return boardRepository.save(requestDto.toEntity());
    }

    @Transactional
    public List<BoardResponseDto> readBoardList(){
        return boardRepository.findAllDesc().
                stream().map(BoardResponseDto :: new).collect(Collectors.toList());
    }
}
