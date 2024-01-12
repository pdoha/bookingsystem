package com.bloodDonation.file.service;

import com.bloodDonation.file.entities.FileInfo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@RequiredArgsConstructor
public class FileDownloadService {
    private final FileInfoService infoService;
    private final HttpServletResponse response;

    public void download(Long seq) {
        FileInfo data = infoService.get(seq);
        //String fileName = data.getFileName();
        String filePath = data.getFilePath();

        //파일명 -> 2바이트 인코딩으로 변경(윈도우즈 시스템에서 한글 깨짐 방지)
        String fileName = null;

        try {
            fileName = new String(data.getFileName().getBytes(), "ISO8859_1");
        } catch (UnsupportedEncodingException e) {

        }

        File file = new File(filePath);

        try (FileInputStream fis = new FileInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            OutputStream out = response.getOutputStream();//응답 Body에 출력

            //fileName으로 출력방향 바꿈
            response.setHeader("Content-Disposition","attachment;filename=" + fileName);
            //octet-stream : 범용적으로 쓰겠다
            response.setHeader("Content-Type","application/octet-stream");
            //만료시간 없앰. 계속 브라우저연결을 하고 있음
            response.setIntHeader("Expires",0);//만료 시간x
            //캐쉬를 하면 안됨
            response.setHeader("Cache-Control","must-revalidate");//다운로드 된 파일은 캐싱이 되어있음. 갱신이 매번됨.
            response.setHeader("Pragma","public");
            response.setHeader("Content-Length", String.valueOf(file.length())); //파일 용량에 대한것

            while (bis.available() > 0) {
                out.write(bis.read());
            }

            out.flush();//한글 파일명은 깨질수 있음

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
