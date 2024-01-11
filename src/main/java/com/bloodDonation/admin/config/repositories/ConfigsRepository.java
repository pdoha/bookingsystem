package com.bloodDonation.admin.config.repositories;

import com.bloodDonation.admin.config.entities.Configs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigsRepository extends JpaRepository<Configs,String> {

}
