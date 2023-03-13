package com.clicktour.clicktour.controller;

import com.clicktour.clicktour.common.message.dto.ExceptionDto;
import com.clicktour.clicktour.common.message.enums.ErrorMessage;
import com.clicktour.clicktour.common.message.enums.SuccessMessage;
import com.clicktour.clicktour.common.message.dto.ResponseDto;
import com.clicktour.clicktour.domain.board.Board;
import com.clicktour.clicktour.domain.board.dto.*;
import com.clicktour.clicktour.service.board.BoardService;
import com.clicktour.clicktour.service.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
public class BoardApiController {

    private final BoardService boardService;
    private final UsersService usersService;

    @PostMapping("/post")
    public ResponseEntity<?> save(@RequestBody BoardSaveRequestDto requestDto){

        Board board = boardService.saveBoard(requestDto);

        if (board == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(new ResponseDto(SuccessMessage.SUCCESS_POST_BOARD), HttpStatus.OK);
    }

    @PostMapping("/comments/post/{id}")
    public ResponseEntity<?> saveComments(@PathVariable Long id, @RequestBody CommentSaveRequestDto commentSaveRequestDto,
                                          HttpServletRequest httpServletRequest){
        String jwtToken = httpServletRequest.getHeader("X-AUTH-TOKEN");

        if(boardService.saveComments(id, usersService.getUserInfo(jwtToken).getNickname(), commentSaveRequestDto) == null){
            return new ResponseEntity<>(new ExceptionDto(ErrorMessage.NOT_FOUND_BOARD), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ResponseDto(SuccessMessage.SUCCESS_POST_COMMENT),HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> readBoardList(){
        List<BoardResponseDto> boardResponseDtoList = boardService.readBoardList();
        if(boardResponseDtoList.isEmpty()){
            return new ResponseEntity<>(new ExceptionDto(ErrorMessage.NOT_FOUND_BOARD), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(boardService.readBoardList(), HttpStatus.OK);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<?> readBoardDetail(@PathVariable Long id){
        BoardDetailResponseDto boardDetailResponseDto = boardService.readDetailBoard(id);
        if(boardDetailResponseDto == null){
            return new ResponseEntity<>(new ExceptionDto(ErrorMessage.NOT_FOUND_BOARD), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(boardDetailResponseDto, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    private ResponseEntity<?> updateBoard(@PathVariable Long id, HttpServletRequest httpServletRequest,
                                          @RequestBody BoardUpdateRequestDto updateRequestDto){
        String jwtToken = httpServletRequest.getHeader("X-AUTH-TOKEN");
        Board board = boardService.updateBoard(id, usersService.getUserInfo(jwtToken).getNickname(), updateRequestDto);

        if(board == null){
            return new ResponseEntity<>(new ExceptionDto(ErrorMessage.NOT_FOUND_BOARD), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ResponseDto(SuccessMessage.SUCCESS_DELETE_BOARD), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long id, HttpServletRequest httpServletRequest){
        String jwtToken = httpServletRequest.getHeader("X-AUTH-TOKEN");

        Board board = boardService.deleteBoard(id, usersService.getUserInfo(jwtToken).getNickname());

        if (board == null){
            return new ResponseEntity<>(new ExceptionDto(ErrorMessage.NOT_FOUND_BOARD), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ResponseDto(SuccessMessage.SUCCESS_DELETE_BOARD), HttpStatus.OK);
    }
}
