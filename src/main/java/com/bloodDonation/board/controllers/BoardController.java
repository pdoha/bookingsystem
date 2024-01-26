package com.bloodDonation.board.controllers;

import com.bloodDonation.board.controllers.comment.RequestComment;
import com.bloodDonation.board.entities.Board;
import com.bloodDonation.board.entities.BoardData;
import com.bloodDonation.board.service.*;
import com.bloodDonation.board.service.config.BoardConfigInfoService;
import com.bloodDonation.commons.ExceptionProcessor;
import com.bloodDonation.commons.ListData;
import com.bloodDonation.commons.Utils;
import com.bloodDonation.commons.exceptions.UnAuthorizedException;
import com.bloodDonation.file.entities.FileInfo;
import com.bloodDonation.file.service.FileInfoService;
import com.bloodDonation.member.MemberUtil;
import com.bloodDonation.member.entities.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController extends AbstractBoardController {
    public BoardController(BoardConfigInfoService configInfoService, FileInfoService fileInfoService, BoardFormValidator boardFormValidator, BoardSaveService boardSaveService, BoardInfoService boardInfoService, BoardDeleteService boardDeleteService, BoardAuthService boardAuthService, MemberUtil memberUtil, Utils utils) {
        super(configInfoService, fileInfoService, boardFormValidator, boardSaveService, boardInfoService, boardDeleteService, boardAuthService, memberUtil, utils);
    }
    /**
     * 게시판 목록
     * @param bid : 게시판 아이디
     * @param model
     * @return
     */
    @GetMapping("/list/{bid}")
    public String list(@PathVariable("bid") String bid,
                       @ModelAttribute BoardDataSearch search, Model model) {
        commonProcess(bid, "list", model);

        ListData<BoardData> data = boardInfoService.getList(bid, search);

        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());

        return utils.tpl("board/list");
    }

    /**
     * 게시글 보기
     *
     * @param seq : 게시글 번호
     * @param model
     * @return
     */
    @GetMapping("/view/{seq}")
    public String view(@PathVariable("seq") Long seq,
                       @ModelAttribute BoardDataSearch search, Model model) {
        boardInfoService.updateViewCount(seq); // 조회수 업데이트

        commonProcess(seq, "view", model);

        // 게시글 보기 하단 목록 노출 S
        if (board.isShowListBelowView()) {
            ListData<BoardData> data = boardInfoService.getList(board.getBid(), search);

            model.addAttribute("items", data.getItems());
            model.addAttribute("pagination", data.getPagination());
        }
        // 게시글 보기 하단 목록 노출 E

        // 댓글 커맨드 객체 처리 S
        RequestComment requestComment = new RequestComment();
        if (memberUtil.isLogin()) {
            requestComment.setCommenter(memberUtil.getMember().getMName());
        }

        model.addAttribute("requestComment", requestComment);
        // 댓글 커맨드 객체 처리 E

        return utils.tpl("board/view");
    }

    /**
     * 게시글 작성
     *
     * @param bid
     * @param model
     * @return
     */
    @GetMapping("/write/{bid}")
    public String write(@PathVariable("bid") String bid,
                        @ModelAttribute RequestBoard form, Model model) {
        commonProcess(bid, "write", model);

        if (memberUtil.isLogin()) {
            Member member = memberUtil.getMember();
            form.setPoster(member.getUserId());
        }

        return utils.tpl("board/write");
    }

    /**
     * 게시글 수정
     *
     * @param seq
     * @param model
     * @return
     */
    @GetMapping("/update/{seq}")
    public String update(@PathVariable("seq") Long seq, Model model) {
        commonProcess(seq, "update", model);

        RequestBoard form = boardInfoService.getForm(boardData);
        model.addAttribute("requestBoard", form);

        return utils.tpl("board/update");
    }

    @GetMapping("/reply/{seq}")
    public String reply(@PathVariable("seq") Long parentSeq,
                        @ModelAttribute RequestBoard form, Model model) {
        commonProcess(parentSeq, "reply", model);
        if (!board.isUseReply()) { // 답글 사용 불가
            throw new UnAuthorizedException();
        }

        String content = boardData.getContent();
        content = String.format("<br><br><br><br><br>===================================================<br>%s", content);

        form.setBid(board.getBid());
        form.setContent(content);
        form.setParentSeq(parentSeq);

        if (memberUtil.isLogin()) {
            form.setPoster(memberUtil.getMember().getMName());
        }

        return utils.tpl("board/write");
    }

    /**
     * 게시글 등록, 수정
     *
     * @param model
     * @return
     */
    @PostMapping("/save")
    public String save(@Valid RequestBoard form, Errors errors, Model model) {
        String bid = form.getBid();
        String mode = form.getMode();
        commonProcess(bid, mode, model);

        boardFormValidator.validate(form, errors);

        if (errors.hasErrors()) {
            String gid = form.getGid();

            List<FileInfo> editorFiles = fileInfoService.getList(gid, "editor");
            List<FileInfo> attachFiles = fileInfoService.getList(gid, "attach");

            form.setEditorFiles(editorFiles);
            form.setAttachFiles(attachFiles);

            return utils.tpl("board/" + mode);
        }

        // 게시글 저장 처리
        BoardData boardData = boardSaveService.save(form);

        String redirectURL = "redirect:/board/";
        redirectURL += board.getLocationAfterWriting().equals("view") ? "view/" + boardData.getSeq() : "list/" + form.getBid();

        return redirectURL;
    }

    @GetMapping("/delete/{seq}")
    public String delete(@PathVariable("seq") Long seq, Model model) {
        commonProcess(seq, "delete", model);

        boardDeleteService.delete(seq);

        return "redirect:/board/list/" + board.getBid();
    }

    /**
     * 비회원 글수정, 글삭제
     * @param password
     * @param model
     * @return
     */
    @PostMapping("/password")
    public String passwordCheck(@RequestParam(name="password", required = false) String password, Model model) {
        boardAuthService.validate(password);

        model.addAttribute("script","parent.location.reload();");

        return "common/_execute_script";
    }

}




