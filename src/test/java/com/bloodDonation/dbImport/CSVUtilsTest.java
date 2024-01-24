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
        String[] fields = {"c_Code", "location", "c_Name", "center_Type", "address", "tel_Num", "zonecode", "address_Sub", "oper_Hour", "book_Yoil", "book_Avl", "book_Not_Avl", "book_Hday", "book_Block", "book_Capacity"};
        List<String> sqlData = csvUtils.makeSql("data/centerInfo.csv", "CENTER_INFO", fields, "'우편번호', NULL, '09:00-18:00', NULL, NULL, NULL, 1, 10, 10", "UTF-8").toList();

        sqlData.forEach(System.out::println);
    }

    @Test
    @DisplayName("CSV 파일 변환 -> SQL 가공 -> sql 파일로 작성 테스트")
    void test3(){
        String destPath = "data/centerLocal.sql";
        String[] fields = {"c_Code", "location", "c_Name", "center_Type", "address", "tel_Num", "zonecode", "address_Sub", "oper_Hour", "book_Yoil", "book_Avl", "book_Not_Avl", "book_Hday", "book_Block", "book_Capacity"};
        csvUtils.makeSql("data/centerInfo.csv", "CENTER_INFO", fields, "'우편번호', NULL, '09:00-18:00', '월,화,수,목,금', '09:30-17:30', '12:00-13:00', 0, 30, 10", "UTF-8").toFile(destPath);
        File file = new File(destPath);

        assertTrue(file.exists());
    }
}
