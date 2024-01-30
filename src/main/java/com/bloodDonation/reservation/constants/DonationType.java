package com.bloodDonation.reservation.constants;

import com.bloodDonation.commons.Utils;

import java.util.Arrays;
import java.util.List;

public enum DonationType { //헌혈종류

    TYPE_ALL(Utils.getMessage("DonationType.TYPE_ALL", "commons")),
    TYPE_A(Utils.getMessage("DonationType.TYPE_A", "commons")),
    TYPE_B(Utils.getMessage("DonationType.TYPE_B", "commons")); //전혈:ALL, 혈장:A, 혈소판: B -> 수정필요

    private final String title;
    DonationType(String title) {
        this.title = title;
    }

    public static List<String[]> getList() {

        return Arrays.asList(
                new String[] { TYPE_ALL.name(), TYPE_ALL.title },
                new String[] { TYPE_A.name(), TYPE_A.title },
                new String[] { TYPE_B.name(), TYPE_B.title}
        );
    }
}
