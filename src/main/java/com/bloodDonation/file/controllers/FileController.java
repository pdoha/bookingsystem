package com.bloodDonation.file.controllers;

import com.bloodDonation.commons.ExceptionProcessor;
import com.bloodDonation.commons.exceptions.AlertBackException;
import com.bloodDonation.commons.exceptions.CommonException;
import com.bloodDonation.file.service.FileDeleteService;
import com.bloodDonation.file.service.FileDownloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 24.01.12
 * 1. 파일의 직접 삭제, 다운로드
 *
 */
@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController implements ExceptionProcessor {

    private final FileDeleteService deleteService;
    private final FileDownloadService downloadService;

    @GetMapping("/delete/{seq}")
    public String delete(@PathVariable("seq") Long seq, Model model) {

        deleteService.delete(seq);

        String script = String.format("if (typeof parent.callbackFileDelete == 'function') parent.callbackFileDelete(%d);", seq);
        //파일 삭제 후 할일이 있으면 정의하고 실행
        model.addAttribute("script", script);

        return "common/_execute_script";
    }

    @ResponseBody
    @RequestMapping("/download/{seq}")
    public void download(@PathVariable("seq") Long seq) {
        try {
            downloadService.download(seq);
        } catch (CommonException e) {
            throw new AlertBackException(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

/*
    @ResponseBody
    @RequestMapping("/download/{seq}")
    public void download(@PathVariable("seq") Long seq, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Disposition","attachment; filename=test.txt");//Content-Disposition : 출력방향을 바꿈. 응답데이터를 filename=test.txt 로 보냄

        PrintWriter out = response.getWriter();
        out.println("test1");
        out.println("test2");
    }
 */

}
