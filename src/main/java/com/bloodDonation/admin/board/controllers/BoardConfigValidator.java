package com.bloodDonation.admin.board.controllers;

import com.bloodDonation.board.repositories.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class BoardConfigValidator implements Validator {

    private final BoardRepository boardRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestBoardConfig.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        /**
         * 24.01.15
         * 게시판 아이디 중복 체크
        */
        RequestBoardConfig form = (RequestBoardConfig) target;

        String bid = form.getBid(); // 게시판 아이디
        String mode = form.getMode(); //게시판 mode
        if (mode.equals("add") && StringUtils.hasText(bid) && boardRepository.existsById(bid)) {
            errors.rejectValue("bid", "Duplicated");
        }
    }
}
