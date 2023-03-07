package com.clicktour.clicktour.controller;

import com.clicktour.clicktour.common.message.SuccessMessage;
import com.clicktour.clicktour.common.message.dto.ResponseDto;
import com.clicktour.clicktour.domain.board.Board;
import com.clicktour.clicktour.domain.board.dto.BoardSaveRequestDto;
import com.clicktour.clicktour.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/post")
    public ResponseEntity<?> save(@RequestBody BoardSaveRequestDto requestDto){
        Board board = boardService.save(requestDto);

        if (board == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(new ResponseDto(SuccessMessage.SUCCESS_POST), HttpStatus.OK);
    }
}
