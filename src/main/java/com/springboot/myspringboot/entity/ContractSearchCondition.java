package com.springboot.myspringboot.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ContractSearchCondition {

    private String contractName;

}
