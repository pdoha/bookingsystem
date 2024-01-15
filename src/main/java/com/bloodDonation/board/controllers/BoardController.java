package com.bloodDonation.board.controllers;

import com.bloodDonation.board.entities.Board;
import com.bloodDonation.board.service.config.BoardConfigInfoService;
import com.bloodDonation.commons.Utils;
import com.bloodDonation.file.entities.FileInfo;
import com.bloodDonation.file.service.FileInfoService;
import com.bloodDonation.member.MemberUtil;
import com.bloodDonation.member.entities.Member;
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
@RequiredArgsConstructor
public class BoardController {
    private final BoardConfigInfoService configInfoService;
    private final FileInfoService fileInfoService;

    private final BoardFormValidator boardFormValidator;

    private final MemberUtil memberUtil;
    private final Utils utils;

    private Board board; // 게시판 설정

    /**
     * 게시판 목록
     * @param bid : 게시판 아이디
     * @param model
     * @return
     */
    @GetMapping("/list/{bid}")
    public String list(@PathVariable("bid") String bid, Model model) {
        commonProcess(bid, "list", model);
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
    public String view(@PathVariable("seq") Long seq, Model model) {
        commonProcess(seq,"view",model);
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
        commonProcess(bid,"write",model);

        if(memberUtil.isLogin()) {
            Member member = memberUtil.getMember();
            //form.setPoster(member.getName());//memberutils에서 넣고 풀기
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
        commonProcess(seq,"update",model);
        return utils.tpl("board/update");
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

        boardFormValidator.validate(form,errors);

        if (errors.hasErrors()) {
            String gid = form.getGid();

            List<FileInfo> editorImages = fileInfoService.getList(gid,"editor");
            List<FileInfo> attachFiles = fileInfoService.getList(gid, "attach");

            return utils.tpl("board/" + mode);
        }

        Long seq = 0L; //임시 - 나중에 제거

        String redirectURL = "/board/";
        redirectURL += board.getLocationAfterWriting() == "view" ? "view/" + seq : "list/" + form.getBid();

        return redirectURL;
    }

    /**
     * 게시판의 공통 처리 - 글 목록, 글쓰기 등 게시판 Id 가 있는 경우
     *
     * @param bid : 게시판 ID
     * @param mode
     * @param model
     */
    private void commonProcess(String bid, String mode, Model model) {

        mode = StringUtils.hasText(mode) ? mode : "list";

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        List<String> addCommonCss = new ArrayList<>();
        List<String> addCss = new ArrayList<>();

        /* 게시판 설정 처리 S */
        board = configInfoService.get(bid);

        //스킨별 css, js 추가
        String skin = board.getSkin();
        addCss.add("board/skin_" + skin);
        addScript.add("board/skin_" + skin);

        model.addAttribute("board", board);
        /* 게시판 설정 처리 E */

        String pageTitle = board.getBName(); //게시판 명이 기본 타이틀

        if (mode.equals("write") || mode.equals("update")) { // 쓰기 또는 수정
            if (board.isUseEditor()) { // 에디터 사용하는 경우
                addCommonScript.add("ckeditor5/ckeditor");
            }
            //이미지또는 파일 첨부를 사용하는 경우
            if (board.isUseUploadFile() || board.isUseUploadFile()) {
                addCommonScript.add("fileManager");
            }

            addScript.add("board/form");

            pageTitle += " ";
            pageTitle += mode.equals("update") ? Utils.getMessage("글수정","commons") : Utils.getMessage("글쓰기","commons");

        }



        model.addAttribute("addCommonCss", addCommonCss);
        model.addAttribute("addCss",addCss);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript",addScript);
    }

    /**
     * 게시판 공통 처리 : 게시글 보기, 게시글 수정 - 게시글 번호가 있는 경우
     *          - 게시글 조홰 -> 게시판 설정
     * @param seq
     * @param mode
     * @param model
     */
    private void commonProcess(Long seq, String mode, Model model){

    }
}
