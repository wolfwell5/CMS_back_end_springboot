package com.springboot.myspringboot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String loginName;
    private String password;

    private String userName;
    private String sex;
    private int age;
    private String email;
    private String company;

    private List<String> contractList;

}
