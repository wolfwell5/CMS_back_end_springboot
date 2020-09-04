package com.springboot.myspringboot.controller;

import com.springboot.myspringboot.api.CommonResult;
import com.springboot.myspringboot.entity.Contract;
import com.springboot.myspringboot.service.ContractOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/contractManagement")
public class ContractOperateController {

    @Autowired
    ContractOperateService service;

    @PostMapping("/saveContract")
    @ResponseBody
    public CommonResult saveContract(@RequestBody Contract contract) {
        if (contract.getCid() == 0) {
            service.saveContractInfo(contract);
        } else {
            service.updateContractInfo(contract);
        }
        return CommonResult.success("data", "保存成功");
    }

    @GetMapping("/contractNameAutoCompletion/{str}")
    @ResponseBody
    public List<Map<String, Object>> contractNameAutoCompletion(@PathVariable(name = "str") String str) {
        return service.contractNameAutoCompletion(str);
    }

    @GetMapping("/searchContractTableData")
    @ResponseBody
    public List<Contract> getTableDataByCondition(Contract contractSearchCondition) {
        return service.getTableDataByCondition(contractSearchCondition);
    }

    @PostMapping("/deleteContractTableData")
    @ResponseBody
    public CommonResult deleteContract(@RequestBody List<String> cids) {
        service.deleteContracts(cids);
        return CommonResult.success(cids.toString(), "删除" + "" + "成功");
    }

    @GetMapping("/getContractById/{cid}")
    @ResponseBody
    public Contract getContractById(@PathVariable(name = "cid") String cid) {
        return service.getContractById(cid);
    }

    @PostMapping("/downloadExcelDataByCondition")
    public void downloadExcelDataByCondition(@RequestParam Map<String, String> map, HttpServletResponse response) {
        service.downLoadExcel(map, response);
    }


}
