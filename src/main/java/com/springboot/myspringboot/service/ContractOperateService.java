package com.springboot.myspringboot.service;

import com.springboot.myspringboot.dao.ContractOperateMapper;
import com.springboot.myspringboot.entity.Contract;
import com.springboot.myspringboot.util.ExcelExportVo;
import com.springboot.myspringboot.util.TicketStatusVo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.springboot.myspringboot.util.ExcelUtil.*;

@Service
public class ContractOperateService {

    @Autowired
    ContractOperateMapper mapper;

    public String findById(String id) {
        return mapper.findById("");
    }

    public int saveContractInfo(Contract contract) {
        int num = mapper.saveContractInfo(contract);
        return num;
    }

    public int updateContractInfo(Contract contract) {
        int num = mapper.updateContractInfo(contract);
        return num;
    }

    public List<Map<String, Object>> contractNameAutoCompletion(String str) {
        List<Map<String, Object>> res = mapper.contractNameAutoCompletion(str);
        return res;
    }


    public List<Contract> getTableDataByCondition(Contract contractSearchCondition) {
        return mapper.getTableDataByCondition(contractSearchCondition);
    }

    public int deleteContracts(List<String> cids) {
        int re = mapper.deleteContracts(cids);
        return re;
    }

    public Contract getContractById(String cid) {
        return mapper.getContractById(cid);
    }

    public ExcelExportVo manipulateData(Contract contractSearchCondition) {

        String searchType = contractSearchCondition.getType();
        Workbook wb = new HSSFWorkbook();
        ExcelExportVo excelExportVo = ExcelExportVo.builder().workbook(wb).rowIdx(0).build();

        if (null != searchType && !"".equalsIgnoreCase(searchType)) {

            List<Contract> tableDataByCondition = this.getTableDataByCondition(contractSearchCondition);
            excelExportVo = generateExcel(excelExportVo, tableDataByCondition, searchType);

            generateSumExcel(excelExportVo, 1, tableDataByCondition.size() + 1);

        } else {
            List<Integer> allLittleSumLine = new ArrayList<>();
//            第一sheet页总计
            contractSearchCondition.setType(TicketStatusVo.ticketStatus1.getStatusType());
            List<Contract> tableDataByCondition1 = this.getTableDataByCondition(contractSearchCondition);
            excelExportVo = generateExcel(excelExportVo, tableDataByCondition1, TicketStatusVo.ticketStatus1.getStatusType());

            generateSumExcel(excelExportVo, 1, tableDataByCondition1.size() + 1);
            allLittleSumLine.add(tableDataByCondition1.size() + 2);

            int startSumRowIdx;
            contractSearchCondition.setType(TicketStatusVo.ticketBackStatus1.getStatusType());
            excelExportVo.setRowIdx(excelExportVo.getRowIdx() + 1 + 1);//空一行 总计
            List<Contract> tableDataByCondition2 = this.getTableDataByCondition(contractSearchCondition);
            excelExportVo = generateExcel(excelExportVo, tableDataByCondition2, TicketStatusVo.ticketBackStatus1.getStatusType());

            int endSumIdx = excelExportVo.getEndSumIdx();
            startSumRowIdx = endSumIdx + 3;
            generateSumExcel(excelExportVo, endSumIdx + 3, tableDataByCondition2.size() + startSumRowIdx);
            allLittleSumLine.add(tableDataByCondition2.size() + startSumRowIdx + 1);
            //  第一sheet页 概况的总体盈亏
            generateOverviewSumData(excelExportVo, allLittleSumLine);

//       第 2 sheet页 详情
            List<Contract> sheet2Data = new ArrayList<>();
//            所有收入 的合同
            List<Contract> list = getAllIncomeContract();
            for (int i = 0; i < list.size(); i++) {
                Contract ci = list.get(i);

                List<Contract> allExpendContract = getAllExpendContract(ci.getCid());
                ci.setChildren(allExpendContract);

                sheet2Data.add(ci);
            }

            excelExportVo.setExcelData(sheet2Data);
            generateExcelSheet2Dtl(excelExportVo);

        }
        //输出Excel文件
//        try {
//            FileOutputStream output = new FileOutputStream("D:\\___编程 learn\\CMS\\cms\\需求以及搭建\\______test.xlsx");
//            wb.write(output);
//            output.flush();
//            if (output != null)
//                output.close();
//        } catch (Exception e) {
//
//        } finally {
//
//        }
        return excelExportVo;
    }


    private List<Contract> getAllIncomeContract() {
        return mapper.getAllIncomeContract();
    }

    private List<Contract> getAllExpendContract(long cid) {
        return mapper.getAllExpendContract(cid);
    }


    public void downLoadExcel(Map<String, String> map, HttpServletResponse response) {

        Map<String, String> m = makeNullStr2Null(map);

        Contract contractSearchCondition = Contract.builder()
                .customer(m.getOrDefault("customer", ""))
                .contractName(m.getOrDefault("contractName", ""))
                .ticketStatus(m.getOrDefault("ticketStatus", ""))
                .contractMoneyStart(m.getOrDefault("contractMoneyStart", ""))
                .contractMoneyEnd(m.getOrDefault("contractMoneyEnd", ""))
                .type(m.getOrDefault("type", ""))
                .build();

        OutputStream outputStream = null;

        ExcelExportVo excelExportVo = this.manipulateData(contractSearchCondition);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");

        try {
            response.flushBuffer();
            outputStream = response.getOutputStream();
            excelExportVo.getWorkbook().write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public List<Contract> getCostSummary() {

//       第 2 sheet页 详情
        List<Contract> sheet2Data = new ArrayList<>();
//            所有收入 的合同
        List<Contract> list = getAllIncomeContract();
        for (int i = 0; i < list.size(); i++) {
            Contract ci = list.get(i);
            String contractMoney = ci.getContractMoney();

            List<Contract> allExpendContract = getAllExpendContract(ci.getCid());

            for (int j = 0; j < allExpendContract.size(); j++) {
                Contract item = allExpendContract.get(j);
                contractMoney = minusStr(contractMoney, item.getContractMoney());
            }
            ci.setContractMoney(contractMoney);

            sheet2Data.add(ci);
        }

        return sheet2Data;
    }
}
