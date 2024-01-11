package com.bloodDonation.admin.config.service;

import com.bloodDonation.admin.config.entities.Configs;
import com.bloodDonation.admin.config.repositories.ConfigsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigDeleteService {
    private final ConfigsRepository repository;

    public void delete(String code) {//한번 조회해서 영속성안에 넣고
        Configs configs = repository.findById(code).orElse(null);
        if(configs == null) {
            return;//함수 연산이 끝나기 때문에 하단의 연산은 진행되지 않음

        }

        repository.delete(configs);//상태만 삭제로 변함.
        repository.flush();
    }
}
