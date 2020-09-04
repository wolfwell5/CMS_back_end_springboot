package com.springboot.myspringboot.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TicketStatusVo {

    ticketStatus1("incomeSearch", "0", "未开票"),
    ticketStatus2("incomeSearch", "1", "已开票"),

    ticketBackStatus1("expendSearch", "0", "未回票"),
    ticketBackStatus2("expendSearch", "1", "已回票");

    private String statusType;
    private String statusVal;
    private String name;

}
