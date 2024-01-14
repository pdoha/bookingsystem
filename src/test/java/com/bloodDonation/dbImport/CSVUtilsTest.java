package com.bloodDonation.dbImport;

import com.bloodDonation.commons.CsvUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CSVUtilsTest {
    @Autowired
    private CsvUtils csvUtils;

    @Test
    @DisplayName("CSV 파일 변환 함수 테스트")
    void test1(){
        List<String[]> lines = csvUtils.getData("data/centerInfo.csv", "EUC-KR");
        lines.forEach(s -> System.out.println(Arrays.toString(s)));
    }

    @Test
    @DisplayName("CSV 파일 변환 후 SQL 가공 함수 테스트")
    void test2(){
        String[] fields = {"cCode", "location", "cName", "centerType", "address", "telNum", "zonecode", "addressSub", "operHour", "bookYoil", "bookAvl", "bookNotAvl", "bookHday", "bookBlock", "bookCapacity"};
        List<String> sqlData = csvUtils.makeSql("data/centerInfo.csv", "CENTER_INFO", fields, "우편번호, 나머지주소, 운영시간, 예약요일, 예약가능시간, 예약불가시간, 공휴일 예약가능여부, 예약블록, 예약가능인원", "EUC-KR").toList();

        sqlData.forEach(System.out::println);
    }

    @Test
    @DisplayName("CSV 파일 변환 -> SQL 가공 -> sql 파일로 작성 테스트")
    void test3(){
        String destPath = "data/center.sql";
        String[] fields = {"cCode", "location", "cName", "centerType", "address", "telNum", "zonecode", "addressSub", "operHour", "bookYoil", "bookAvl", "bookNotAvl", "bookHday", "bookBlock", "bookCapacity"};
        csvUtils.makeSql("data/centerInfo.csv", "CENTER_INFO", fields, "우편번호, 나머지주소, 운영시간, 예약요일, 예약가능시간, 예약불가시간, 공휴일 예약가능여부, 예약블록, 예약가능인원", "EUC-KR").toFile(destPath);
        File file = new File(destPath);

        assertTrue(file.exists());
    }
}
