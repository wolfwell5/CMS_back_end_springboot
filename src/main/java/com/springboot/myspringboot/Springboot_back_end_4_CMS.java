package com.springboot.myspringboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("mybatis")//启动类中添加这句，扫描dao文件
public class Springboot_back_end_4_CMS {

    public static void main(String[] args) {

        SpringApplication.run(Springboot_back_end_4_CMS.class, args);
    }

}
