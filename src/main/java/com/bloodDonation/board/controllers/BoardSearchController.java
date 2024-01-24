package com.bloodDonation.board.controllers;

import com.bloodDonation.board.entities.Board;
import com.bloodDonation.board.entities.BoardData;
import com.bloodDonation.board.service.BoardInfoService;
import com.bloodDonation.commons.ExceptionProcessor;
import com.bloodDonation.commons.ListData;
import com.bloodDonation.commons.Utils;
import com.bloodDonation.commons.exceptions.AlertBackException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board/search")
@RequiredArgsConstructor
public class BoardSearchController implements ExceptionProcessor {

    private final Utils utils;
    private final BoardInfoService boardInfoService;

    @ModelAttribute("pageTitle")
    public String pageTitle() {
        return Utils.getMessage("게시글_통합검색","commons");
    }

    @ModelAttribute("addCss")
    public String[] addCss() {
        return new String[] {"board/skin_default"};
    }

    @GetMapping
    public String search(@ModelAttribute BoardDataSearch search, Model model) {

        if(!StringUtils.hasText(search.getSkey())) {
            throw new AlertBackException(Utils.getMessage("NotBlank","skey"), HttpStatus.BAD_REQUEST);
        }

        String sopt = search.getSopt();
        if(!StringUtils.hasText(sopt)) {
            search.setSopt("SUBJECT_CONTENT");
        }

        Board board = new Board();
        board.setSkin("default");

        ListData<BoardData> data = boardInfoService.getList(search);

        model.addAttribute("items",data.getItems());
        model.addAttribute("pagination", data.getPagination());
        model.addAttribute("board",board);
        model.addAttribute("mode","search");

        return utils.tpl("board/search");
    }

}
