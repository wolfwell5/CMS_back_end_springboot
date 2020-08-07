package com.springboot.myspringboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
@MapperScan("mybatis")//启动类中添加这句，扫描dao文件
public class MyspringbootApplication {

    public static void main(String[] args) {

        SpringApplication.run(MyspringbootApplication.class, args);
    }

}
