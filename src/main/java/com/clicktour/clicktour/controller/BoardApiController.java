package com.clicktour.clicktour.controller;

import com.clicktour.clicktour.common.message.dto.ExceptionDto;
import com.clicktour.clicktour.common.message.enums.ErrorMessage;
import com.clicktour.clicktour.common.message.enums.SuccessMessage;
import com.clicktour.clicktour.common.message.dto.ResponseDto;
import com.clicktour.clicktour.common.service.CheckService;
import com.clicktour.clicktour.domain.board.Board;
import com.clicktour.clicktour.domain.board.dto.BoardDetailResponseDto;
import com.clicktour.clicktour.domain.board.dto.BoardResponseDto;
import com.clicktour.clicktour.domain.board.dto.BoardSaveRequestDto;
import com.clicktour.clicktour.domain.board.dto.CommentSaveRequestDto;
import com.clicktour.clicktour.domain.users.dto.UserInfoResponseDto;
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
@RequestMapping("api/v1/board")
public class BoardApiController {

    private final BoardService boardService;
    private final CheckService checkService;
    private final UsersService usersService;

    @PostMapping("/post")
    public ResponseEntity<?> save(@RequestBody BoardSaveRequestDto requestDto, HttpServletRequest httpServletRequest){

        if(checkService.checkJwtToken(httpServletRequest.getHeader("X-AUTH-TOKEN"))) {
            return new ResponseEntity<>(new ExceptionDto(ErrorMessage.FORBIDDEN), HttpStatus.FORBIDDEN);
        }

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

        if(checkService.checkJwtToken(jwtToken)) {
            return new ResponseEntity<>(new ExceptionDto(ErrorMessage.FORBIDDEN), HttpStatus.FORBIDDEN);
        }

        UserInfoResponseDto userInfoResponseDto = usersService.getUserInfo(jwtToken);
        if(boardService.saveComments(id,userInfoResponseDto.getNickname(), commentSaveRequestDto) == null){
            return new ResponseEntity<>(new ExceptionDto(ErrorMessage.NOT_FOUND_BOARD), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ResponseDto(SuccessMessage.SUCCESS_POST_COMMENT),HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> readBoardList(){
        List<BoardResponseDto> boardResponseDtoList = boardService.readBoardList();
        if(boardResponseDtoList.isEmpty()){
            return new ResponseEntity<>(new ExceptionDto(ErrorMessage.NOT_FOUND_BOARD), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(boardService.readBoardList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readBoardDetail(@PathVariable Long id){
        BoardDetailResponseDto boardDetailResponseDto = boardService.readDetailBoard(id);
        if(boardDetailResponseDto == null){
            return new ResponseEntity<>(new ExceptionDto(ErrorMessage.NOT_FOUND_BOARD), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(boardDetailResponseDto, HttpStatus.OK);
    }
}
