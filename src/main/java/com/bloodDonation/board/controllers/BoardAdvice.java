package com.bloodDonation.board.controllers;

import com.bloodDonation.board.entities.Board;
import com.bloodDonation.board.service.config.BoardConfigInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice("com.boolDonation")
@RequiredArgsConstructor
public class BoardAdvice {
    private final BoardConfigInfoService configInfoService;

    @ModelAttribute("boardMenus")
    public List<Board> getBoardList() {
        return configInfoService.getList();
    }
}
