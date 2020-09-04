package com.springboot.myspringboot.util;

import com.springboot.myspringboot.entity.Contract;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelUtil {

    static List<String> transferList = Arrays.asList("ticketStatus", "ticketBackStatus");
    static String incomeOrExpendTypeStatic = "incomeSearch";
    static String aOrB = "2";
    static String headerType = "1";
    static String fmt = "yyyy年m月d日";

    public static ExcelExportVo generateExcel(ExcelExportVo excelExportVo, List<Contract> data, String incomeOrExpendType) {

        excelExportVo.setExcelData(data);
        Workbook wb = excelExportVo.getWorkbook();
        int rowIdx = excelExportVo.getRowIdx();
        Sheet sheet;
        incomeOrExpendTypeStatic = incomeOrExpendType;
        aOrB = incomeOrExpendType.equalsIgnoreCase("incomeSearch") ? "1" : "2";

        try {

            if (wb.getNumberOfSheets() > 0) {
                sheet = wb.getSheetAt(0);
            } else {
                sheet = wb.createSheet("总体概况");
            }

            List<ExcelHeader> actualHeaders = generateActualHeaders(headerType, aOrB);
            rowIdx = writeHeader2Wb(wb, sheet, rowIdx, actualHeaders);
            rowIdx = generate_WriteRows(wb, sheet, rowIdx + 1, data, actualHeaders);
            excelExportVo.setRowIdx(rowIdx);
            excelExportVo.setActualHeaders(actualHeaders);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return excelExportVo;
    }

    public static void generateExcelSheet2Dtl(ExcelExportVo excelExportVo) {

        HSSFWorkbook wb = (HSSFWorkbook) excelExportVo.getWorkbook();
        Sheet sheet2 = wb.createSheet("详情");
        int rowIdx = 0;
        List<Contract> data = excelExportVo.getExcelData();

        List<ExcelHeader> actualHeaders = generateActualHeaders(headerType, "1");
        rowIdx = writeHeader2Wb(wb, sheet2, rowIdx, actualHeaders);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFDataFormat format = wb.createDataFormat();
        cellStyle.setDataFormat(format.getFormat(fmt));

        int startSumRowIdx = 1;
        int endSumIdx = 1;

        for (int i = 0; i < data.size(); i++) {
            Row row = sheet2.createRow(++rowIdx);
            Contract ci = data.get(i);

            setRowDtlFirstData(wb, actualHeaders, cellStyle, row, ci);
            rowIdx++;
            startSumRowIdx = rowIdx;

            List<Contract> children = ci.getChildren();
            for (int j = 0; j < children.size(); j++) {
                row = sheet2.createRow(rowIdx);
                Contract cj = children.get(j);
                setRowDtl(actualHeaders, cellStyle, row, cj);
                rowIdx++;

            }
            endSumIdx = rowIdx;
//            构造 小计 函数
            excelExportVo.setSheetIdx(1);
            excelExportVo.setRowIdx(rowIdx);
            generateSumExcel(excelExportVo, startSumRowIdx, endSumIdx);

        }

    }

    public static void setRowDtlFirstData(HSSFWorkbook wb, List<ExcelHeader> actualHeaders, HSSFCellStyle cellStyle, Row row, Contract ci) {

        HSSFCellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        for (int j = 0; j < actualHeaders.size(); j++) {
            Cell cell = row.createCell(j);
            String colName = actualHeaders.get(j).getColName();
            Object actualVal = getActualVal(ci, colName);
            cell.setCellStyle(style);//第一行数据 均为绿色


            if (actualVal instanceof String) {
                String str = actualVal.toString();
                // 回票情况 转换
                if (transferList.contains(colName)) {
                    String ticketStatusName = getStatusName(str);
                    str = ticketStatusName;
                    cell.setCellValue(str);
                } else if (colName.indexOf("Money") != -1) {
                    cell.setCellValue(Double.parseDouble(str));
                } else {
                    cell.setCellValue(str);
                }

            } else if (actualVal instanceof Date) {
                cell.setCellStyle(cellStyle);
                cell.setCellValue((Date) actualVal);
            }

        }
    }

    public static void setRowDtl(List<ExcelHeader> actualHeaders, HSSFCellStyle cellStyle, Row row, Contract ci) {
        for (int j = 0; j < actualHeaders.size(); j++) {
            Cell cell = row.createCell(j);
            String colName = actualHeaders.get(j).getColName();
            Object actualVal = getActualVal(ci, colName);

            if (actualVal instanceof String) {
                String str = actualVal.toString();
                // 回票情况 转换
                if (transferList.contains(colName)) {
                    String ticketStatusName = getStatusName(str);
                    str = ticketStatusName;
                    cell.setCellValue(str);
                } else if (colName.indexOf("Money") != -1) {
                    cell.setCellValue(-Double.parseDouble(str));
                } else {
                    cell.setCellValue(str);
                }

            } else if (actualVal instanceof Date) {
                cell.setCellStyle(cellStyle);
                cell.setCellValue((Date) actualVal);
            }

        }
    }

    public static int generate_WriteRows(Workbook wb, Sheet sheet, int rowIdx, List<Contract> data, List<ExcelHeader> actualHeaders) {

        HSSFWorkbook wbNew = (HSSFWorkbook) wb;

        HSSFCellStyle cellStyle = wbNew.createCellStyle();
        HSSFDataFormat format = wbNew.createDataFormat();
        cellStyle.setDataFormat(format.getFormat(fmt));


        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(rowIdx);
            Contract contract = data.get(i);

            for (int j = 0; j < actualHeaders.size(); j++) {
                Cell cell = row.createCell(j);
                String colName = actualHeaders.get(j).getColName();
                Object actualVal = getActualVal(contract, colName);

                if (actualVal instanceof String) {
                    String str = actualVal.toString();
                    // 回票情况 转换
                    if (transferList.contains(colName)) {
                        String ticketStatusName = getStatusName(str);
                        str = ticketStatusName;
                        cell.setCellValue(str);
                    } else if (colName.indexOf("Money") != -1) {
                        cell.setCellValue(Double.parseDouble(str));
                    } else {
                        cell.setCellValue(str);
                    }

                } else if (actualVal instanceof Date) {
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue((Date) actualVal);
                }

            }
            rowIdx++;
        }

        return rowIdx;
    }


    public static String getStatusName(String str) {

        String res = "未开票";
        TicketStatusVo[] ticketStatusVos = TicketStatusVo.values();

        for (int i = 0; i < ticketStatusVos.length; i++) {
            TicketStatusVo item = ticketStatusVos[i];
            if (incomeOrExpendTypeStatic.equalsIgnoreCase(item.getStatusType()) && str.equalsIgnoreCase(item.getStatusVal())) {
                res = item.getName();
                return res;
            }
        }

        return res;
    }

    public static Object getActualVal(Contract contract, String colName) {

        String methodStr = "get" + colName.substring(0, 1).toUpperCase() + colName.substring(1);
        Class clz = Contract.class;
        Object invoke = null;
        Method method;

        try {
            method = clz.getDeclaredMethod(methodStr);
            invoke = method.invoke(contract);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return invoke;
    }

    public static int writeHeader2Wb(Workbook wb, Sheet sheet, int rowIdx, List<ExcelHeader> actualHeaders) {

        CellStyle cellStyle = getCellStyleBold(wb);
// 设置背景色
        if (rowIdx == 0) {
            cellStyle.setFillForegroundColor(IndexedColors.LIME.getIndex());
        } else {
            cellStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
//            cellStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        }
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        Row row = sheet.createRow(rowIdx);

        for (int i = 0; i < actualHeaders.size(); i++) {
            ExcelHeader item = actualHeaders.get(i);
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(item.getHeaderName());

            sheet.setColumnWidth(i, 252 * item.getExcelColWidth());
        }
        return rowIdx;
    }

    public static CellStyle getCellStyleBold(Workbook wb) {
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("仿宋_GB2312");
//        font.setColor(Font.COLOR_RED);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        int width = 15;
//创建单元格格式CellStyle
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(font);
        return cellStyle;
    }

    public static List<ExcelHeader> generateActualHeaders(String type, String aOrB) {

        List<ExcelHeader> actualHeaders = new ArrayList();
        ExcelHeader[] headers = ExcelHeader.values();

        for (int i = 0; i < headers.length; i++) {
            ExcelHeader item = headers[i];
            String itemStr = item.toString();
            String itemType = item.getType();

            if (itemType.equalsIgnoreCase(type)) {
                if (item.isAlwaysShow()) {
                    actualHeaders.add(item);
                } else if (itemStr.indexOf(aOrB) > 0) {// 收入时，传 "",显示支出列时，传 2
                    actualHeaders.add(item);
                }
            }
        }
        return actualHeaders;
    }


    public static ExcelExportVo generateSumData(ExcelExportVo excelExportVo) {

        int rowIdx = excelExportVo.getRowIdx();
        int startSumIdx = excelExportVo.getStartSumIdx();
        int endSumIdx = excelExportVo.getEndSumIdx();
        Workbook wb = excelExportVo.getWorkbook();
        Sheet sheet = wb.getSheetAt(excelExportVo.getSheetIdx());
        Row rowSum = sheet.createRow(rowIdx);

        CellStyle cellStyleBold = getCellStyleBold(wb);
        Cell cell = rowSum.createCell(0);
        cell.setCellStyle(cellStyleBold);
        cell.setCellValue("合计");

        List<ExcelHeader> moneyHeaders = excelExportVo.getActualHeaders().stream().filter(it -> it.getColName().indexOf("Money") >= 0).collect(Collectors.toList());

        for (int i = 0; i < moneyHeaders.size(); i++) {
            ExcelHeader item = moneyHeaders.get(i);
            String colAbc = item.toString();
            String colStr = colAbc.substring(3, 4);
            String sumString = "SUM(" + colStr + startSumIdx + ":" + colStr + endSumIdx + ")";//求和公式
            int colIdx = CellReference.convertColStringToIndex(colStr);
            rowSum.createCell(colIdx).setCellFormula(sumString);
        }
        return excelExportVo;
    }

    public static ExcelExportVo generateOverviewSumData(ExcelExportVo excelExportVo, List<Integer> allLittleSumLine) {

        int rowIdx = excelExportVo.getRowIdx() + 3;
        Workbook wb = excelExportVo.getWorkbook();
        Sheet sheet = wb.getSheetAt(excelExportVo.getSheetIdx());
        Row rowSum = sheet.createRow(rowIdx);

        CellStyle cellStyleBold = getCellStyleBold(wb);
        Cell cell = rowSum.createCell(0);
        cell.setCellStyle(cellStyleBold);
        cell.setCellValue("总体盈利");

        List<ExcelHeader> moneyHeaders = excelExportVo.getActualHeaders().stream().filter(it -> it.getColName().indexOf("Money") >= 0).collect(Collectors.toList());

        for (int i = 0; i < moneyHeaders.size(); i++) {
            ExcelHeader item = moneyHeaders.get(i);
            String colAbc = item.toString();
            String colStr = colAbc.substring(3, 4);
//            =SUM(C16,C9,D9,F9)
            int minusLineStart = 1;
            String sign = "";
            String sumString = "SUM(";
            for (int j = 0; j < allLittleSumLine.size(); j++) {
                int line = allLittleSumLine.get(j);
                if (j >= minusLineStart) {
                    sign = "-";
                }
                sumString += sign + colStr + line + ",";//求和公式
            }
            sumString = sumString.substring(0, sumString.length() - 1) + ")";

            int colIdx = CellReference.convertColStringToIndex(colStr);
            rowSum.createCell(colIdx).setCellFormula(sumString);
        }
        return excelExportVo;
    }


    public static Map<String, String> makeNullStr2Null(Map map) {

        Map m = new HashMap();

        Set<Map.Entry<String, String>> set = map.entrySet();

        for (Map.Entry<String, String> en : set) {

            String k = en.getKey();
            String v = en.getValue();

            m.put(k, v.equalsIgnoreCase("null") ? null : v);

        }

        return m;
    }

    public static void generateSumExcel(ExcelExportVo excelExportVo, int startSumRowIdx, int endSumRowIdx) {
        excelExportVo.setStartSumIdx(startSumRowIdx);
        excelExportVo.setEndSumIdx(endSumRowIdx);
        generateSumData(excelExportVo);
    }

}
