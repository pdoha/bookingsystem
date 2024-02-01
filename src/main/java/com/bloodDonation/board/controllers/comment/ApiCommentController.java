package com.bloodDonation.board.controllers.comment;

import com.bloodDonation.board.entities.CommentData;
import com.bloodDonation.board.service.BoardAuthService;
import com.bloodDonation.board.service.comment.CommentInfoService;
import com.bloodDonation.board.service.comment.CommentSaveService;
import com.bloodDonation.commons.ExceptionProcessor;
import com.bloodDonation.commons.rests.JSONData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class ApiCommentController implements ExceptionProcessor {

    private final CommentInfoService commentInfoService;
    private final CommentSaveService commentSaveService;
   private final BoardAuthService boardAuthService;

    @GetMapping("/{seq}")
    public JSONData<CommentData> getComment(@PathVariable("seq") Long seq) {

        CommentData data = commentInfoService.get(seq);

        return new JSONData<>(data);
    }

    @PatchMapping
    public JSONData<Object> editComment(RequestComment form) {

        boardAuthService.check("comment_update", form.getSeq());

        form.setMode("edit");
        commentSaveService.save(form);

        return new JSONData<>();
    }

    @GetMapping("/auth_check")
    public JSONData<Object> authCheck(@RequestParam("seq") Long seq) {

        boardAuthService.check("comment_update", seq);
        return new JSONData<>();
    }

    @GetMapping("/auth_validate")
    public JSONData<Object> authValidate(@RequestParam("password") String password) {
        boardAuthService.validate(password);

        return new JSONData<>();
    }

}
