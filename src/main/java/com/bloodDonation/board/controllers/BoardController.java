package com.bloodDonation.board.controllers;

import com.bloodDonation.board.entities.BoardData;
import com.bloodDonation.board.repositories.BoardDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardDataRepository boardDataRepository;

    @ResponseBody
    @GetMapping("/test")

    public void test() {
        BoardData data = boardDataRepository.findById(1L).orElse(null);
        data.setSubject("(수정)제목");
        boardDataRepository.flush();

    }

    @ResponseBody
    @GetMapping("/test2")
   // @PreAuthorize("isAuthenticated()")
    @PreAuthorize("hasAuthority('ADMIN')")
    //@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
    public void test2(){
        System.out.println("test2!!");
    }
}

