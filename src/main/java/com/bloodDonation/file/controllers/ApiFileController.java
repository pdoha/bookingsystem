package com.bloodDonation.file.controllers;

import com.bloodDonation.commons.ExceptionRestProcessor;
import com.bloodDonation.commons.rests.JSONData;
import com.bloodDonation.file.entities.FileInfo;
import com.bloodDonation.file.service.FileDeleteService;
import com.bloodDonation.file.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
    @RequestMapping("/api/file")
    @RequiredArgsConstructor
    public class ApiFileController implements ExceptionRestProcessor {//ExceptionRestProcessor : 예외 나면 예외처리하라고 넣음

        private final FileUploadService uploadService;
        private final FileDeleteService deleteService;
    /**
     * 24.01.12
     * 1. 업로드 파일의 gid,location등 텍스트 파일을 보냄
     * MultipartFile[] : 여러개의 파일을 올림
     */

        @PostMapping
        public JSONData<List<FileInfo>> upload(@RequestParam("file") MultipartFile[] files,
                                               @RequestParam(name="gid", required = false) String gid,
                                               @RequestParam(name="location", required = false) String location,
                                               @RequestParam(name="imageOnly",required=false) boolean imageOnly,
                                               @RequestParam(name="singleFile", required = false)boolean singleFile) {//gid는 필수

            List<FileInfo> uploadedFiles =  uploadService.upload(files,gid,location,imageOnly, singleFile);
            return new JSONData<>(uploadedFiles);

    }

    @GetMapping("/{seq}")
    public void delete(@PathVariable("seq") Long seq) {

            deleteService.delete(seq);
    }

}
