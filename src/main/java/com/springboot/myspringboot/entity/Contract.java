package com.springboot.myspringboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Contract {


    long rownum;
    long cid;
    String customer;
    String contractName;
    String contractMoney;
    String receiveMoney;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    Date receiveDate;
    String insuranceMoney;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    Date insuranceDueDate;
    String ticketStatus;
    String ticketCompany;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    Date ticketGenerateDate;
    String comment;

    long pid;
    String payMoney;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    Date payDate;
    String ticketBackStatus;
    String ticketBackCompany;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    Date ticketBackDate;

    @JsonIgnore
    String contractMoneyStart;
    @JsonIgnore
    String contractMoneyEnd;
    @JsonIgnore
    String type;

}
