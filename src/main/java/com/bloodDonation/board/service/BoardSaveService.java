package com.bloodDonation.board.service;

import com.bloodDonation.board.controllers.RequestBoard;
import com.bloodDonation.board.entities.BoardData;
import com.bloodDonation.board.repositories.BoardDataRepository;
import com.bloodDonation.file.service.FileUploadService;
import com.bloodDonation.member.MemberUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class BoardSaveService {
    private final BoardDataRepository boardDataRepository;
    private final FileUploadService fileUploadService;
    private final MemberUtil memberUtil;
    private final HttpServletRequest request;

    public BoardData save(RequestBoard form) {
        String mode = form.getMode();
        mode = StringUtils.hasText(mode) ? mode: "write";

        Long seq = form.getSeq();

        BoardData data = null;

        if (seq != null && mode.equals("update")) {//글수정
            data = boardDataRepository.findById(seq).orElseThrow(BoardDataNotFoundException::new);
        } else { //글 작성
            data = new BoardData();
            data.setGid(form.getGid());
            data.setIp(request.getRemoteAddr());
            data.setUa(request.getHeader("User-Agent"));
            data.setMember(memberUtil.getMember());
        }

        return data;
    }

}
