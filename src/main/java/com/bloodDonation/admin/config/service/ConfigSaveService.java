package com.bloodDonation.admin.config.service;

import com.bloodDonation.admin.config.entities.Configs;
import com.bloodDonation.admin.config.repositories.ConfigsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigSaveService {//저장처리
    private final ConfigsRepository repository;

    public void save(String code, Object data) {//설정의 코드와 데이터를 가져옴
        Configs configs = repository.findById(code).orElseGet(Configs::new);
        //조회가 되면 영속성 상태여서 수정, 조회가 안되면 orelseget으로 생성
        //반환값이 optional(orElseGet)임.

        ObjectMapper om = new ObjectMapper();//json과 자바를 왔다갔다.java 타임 패키지
        om.registerModule(new JavaTimeModule());

        try {
            String jsonString = om.writeValueAsString(data);//자바 객체를 json 문자열로 바꿔주는
            configs.setData(jsonString);
            configs.setCode(code);
            repository.saveAndFlush(configs);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
