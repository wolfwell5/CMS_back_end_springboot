package com.springboot.myspringboot.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExcelHeader {

    colA("往来客户", "customer", true, "1", 30),
    colB("合同名称", "contractName", true, "1", 30),
    colC("合同金额", "contractMoney", true, "1", 8),
    colD1("收款金额", "receiveMoney", false, "1", 8),
    colD2("付款金额", "payMoney", false, "1", 8),

    colE1("收款日期", "receiveDate", false, "1", 13),
    colE2("付款日期", "payDate", false, "1", 13),
    colF("质保金", "insuranceMoney", true, "1", 8),
    colG("质保金到期日", "insuranceDueDate", true, "1", 13),
    colH1("开票情况", "ticketStatus", false, "1", 8),

    colH2("回票情况", "ticketBackStatus", false, "1", 8),
    colI1("开票单位", "ticketCompany", false, "1", 25),
    colI2("回票单位", "ticketBackCompany", false, "1", 25),
    colJ1("开票日期", "ticketGenerateDate", false, "1", 13),
    colJ2("回票日期", "ticketBackDate", false, "1", 13),
    colK("备注", "comment", true, "1", 30),

    colSumA("内部项目负责人", "projectInnerPerson", true, "2", 10),
    colSumB("项目名称", "projectName", true, "2", 30),
    colSumC("项目金额", "projectMoney", true, "2", 8),
    colSumD("项目成本支出", "projectCost", true, "2", 8);

    private String headerName;
    private String colName;
    private boolean alwaysShow;
    private String type;
    private int excelColWidth;


}
