package com.bloodDonation.board.service;

import com.bloodDonation.board.entities.BoardData;
import com.bloodDonation.board.repositories.BoardDataRepository;
import com.bloodDonation.file.service.FileDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardDeleteService {
    private final BoardDataRepository boardDataRepository;
    private final BoardInfoService boardInfoService;
    private final FileDeleteService fileDeleteService;
    private final BoardAuthService boardAuthService;

    /**
     * 게시글 삭제
     *
     * @param seq
     */
    public void delete(Long seq) {

        //삭제 권한 체크
        boardAuthService.check("delete",seq);

        BoardData data = boardInfoService.get(seq);

        String gid = data.getGid();

        boardDataRepository.delete(data);
        boardDataRepository.flush();

        // 업로된 파일 삭제
        fileDeleteService.delete(gid);
    }
}
