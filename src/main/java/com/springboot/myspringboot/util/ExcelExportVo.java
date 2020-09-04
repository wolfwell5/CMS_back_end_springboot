package com.springboot.myspringboot.util;

import lombok.Builder;
import lombok.Data;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

@Data
@Builder
public class ExcelExportVo {
    Workbook workbook;
    int rowIdx;
    int startSumIdx;
    int endSumIdx;
    int sheetIdx;

    List<ExcelHeader> actualHeaders;
    List excelData;
}
