package com.bloodDonation.admin.file;

import com.bloodDonation.file.service.FileInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class ThumbnailTest {
    @Autowired
    private FileInfoService infoService;

    @Test
    void getThumbTest() {
        String[] data = infoService.getThumb(302L,150,150);
        System.out.println(Arrays.toString(data));
    }
    //썸네일을 이미지태그로 바로 출력

}
