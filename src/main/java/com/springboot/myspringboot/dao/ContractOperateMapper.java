package com.springboot.myspringboot.dao;

import com.springboot.myspringboot.entity.Contract;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ContractOperateMapper {

    String findById(String id);

    String findById2(String id);

    //    @Insert("insert into contract (customer,contractName,contractMoney,receiveMoney,receiveDate" +
//            ",insuranceMoney,insuranceDueDate,ticketStatus,ticketCompany,ticketGenerateDate" +
//            ",comment) " +
//
//            "values(#{customer},#{contractName},#{contractMoney},#{receiveMoney},#{receiveDate}," +
//            "#{insuranceMoney},#{insuranceDueDate},#{ticketStatus},#{ticketCompany},#{ticketGenerateDate}," +
//            "#{comment})")
    int saveContractInfo(Contract contract);

    int updateContractInfo(Contract contract);

    //    @Select("select customer from contract c where c.customer like CONCAT('%',#{str},'%')")
//    @Select("select customer from contract c where c.customer, like '% ${str} %' ")
    List<Map<String, Object>> contractNameAutoCompletion(@Param("str") String str);

    List<Contract> getTableDataByCondition(Contract contractSearchCondition);

    int deleteContracts(@Param("cids") List<String> cids);

    Contract getContractById(@Param("cid") String cid);

    List<Contract> getAllIncomeContract();

    List<Contract> getAllExpendContract(@Param("cid") long cid);
}
