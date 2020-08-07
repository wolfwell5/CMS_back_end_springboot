package com.springboot.myspringboot.service;

import com.springboot.myspringboot.dao.ContractOperateMapper;
import com.springboot.myspringboot.entity.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ContractOperateService {

    @Autowired
    ContractOperateMapper mapper;

    public String findById(String id) {
        return mapper.findById("");
    }

    public void saveContractInfo(Contract contract) {
        int num = mapper.saveContractInfo(contract);
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
}
