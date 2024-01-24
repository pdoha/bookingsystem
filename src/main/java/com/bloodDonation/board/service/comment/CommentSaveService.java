package com.bloodDonation.board.service.comment;

import com.bloodDonation.board.controllers.comment.RequestComment;
import com.bloodDonation.board.entities.BoardData;
import com.bloodDonation.board.entities.CommentData;
import com.bloodDonation.board.repositories.BoardDataRepository;
import com.bloodDonation.board.repositories.CommentDataRepository;
import com.bloodDonation.board.service.BoardDataNotFoundException;
import com.bloodDonation.member.MemberUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class CommentSaveService {
    private final CommentDataRepository commentDataRepository;
    private final BoardDataRepository boardDataRepository;

    private final CommentInfoService commentInfoService;

    private final MemberUtil memberUtil;
    private final PasswordEncoder encoder;
    private final HttpServletRequest request;

    public CommentData save(RequestComment form) {

        String mode = form.getMode();
        Long seq = form.getSeq(); // 댓글 번호

        mode = StringUtils.hasText(mode) ? mode : "add";

        CommentData data = null;
        if (mode.equals("edit") && seq != null) { // 댓글 수정
            data = commentDataRepository.findById(seq).orElseThrow(CommentNotFoundException::new);

        } else { // 댓글 추가
            data = new CommentData();
            // 게시글 번호는 변경 X -> 추가할 때 최초 1번만 반영
            Long boardDataSeq = form.getBoardDataSeq();
            BoardData boardData = boardDataRepository.findById(boardDataSeq).orElseThrow(BoardDataNotFoundException::new);

            data.setBoardData(boardData);

            data.setMember(memberUtil.getMember()); // 추가할 때 최초 1번만 반영

            data.setIp(request.getRemoteAddr());
            data.setUa(request.getHeader("User-Agent"));
        }

        // 비회원 비밀번호 O -> 해시화 -> 저장
        String guestPw = form.getGuestPw();
        if (StringUtils.hasText(guestPw)) {
            data.setGuestPw(encoder.encode(guestPw));
        }

        String commenter = form.getCommenter();
        if(StringUtils.hasText(commenter)) {
            data.setCommenter(commenter);//작성자 바꿀수 있음
        }

        data.setContent(form.getContent());//댓글 내용 바꿀수 있음

        commentDataRepository.saveAndFlush(data);

        commentInfoService.updateCommentCount(form.getBoardDataSeq());

        return data;

    }
}
