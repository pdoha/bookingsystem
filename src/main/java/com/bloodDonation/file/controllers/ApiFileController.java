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
    public class ApiFileController implements ExceptionRestProcessor {

        private final FileUploadService uploadService;
        private final FileDeleteService deleteService;


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
