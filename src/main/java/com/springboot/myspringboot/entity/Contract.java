package com.springboot.myspringboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class Contract {

    private long rownum;
    private long cid;
    //@JsonProperty(value = "firstName")
    private String customer;
    private String contractName;
    private String contractMoney;
    private String receiveMoney;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date receiveDate;
    private String insuranceMoney;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date insuranceDueDate;
    private String ticketStatus;
    private String ticketCompany;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date ticketGenerateDate;
    private String comment;

    private long pid;
    private String customerSource;
    private String payMoney;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date payDate;
    private String ticketBackStatus;
    private String ticketBackCompany;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date ticketBackDate;

    @JsonIgnore
    private String contractMoneyStart;
    @JsonIgnore
    private String contractMoneyEnd;
    @JsonIgnore
    private String type;
    @JsonIgnore
    List<Contract> children;
}
