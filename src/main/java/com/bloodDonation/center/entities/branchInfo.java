package com.bloodDonation.center.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class branchInfo {

    @Id
    @Column
    private String C_Code;

    //private String


}
