package com.springboot.myspringboot.controller;

import com.springboot.myspringboot.entity.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

//https://blog.csdn.net/suki_rong/article/details/80445880
@Controller
@RequestMapping("/test")
public class HelloController {

    @GetMapping("/hi/{id}")
    @ResponseBody
    public String hello(@PathVariable(name = "id") String id, @RequestParam(name = "name") String name) {

        return "hello my friend !  id:  " + id + ", name: " + name;
    }

    @PostMapping("/upload")
    @ResponseBody
    public Person upload(@RequestBody Person person) {
        Person p = Person.builder().name(person.getName())
                .sex(person.getSex())
                .age(person.getAge()).build();

        return p;
    }

//    第三类：请求头参数以及Cookie
//1、@RequestHeader
//2、@CookieValue

    @GetMapping("/getHeader")
    public void getHeader(HttpServletRequest request) {

        Enumeration<String> headerNames = request.getHeaderNames();
        String header = request.getHeader("host");
        System.out.println(header);

        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            System.out.println(cookies[i]);
        }
    }

    //    RequestBody接收xml格式数据，自动转为map接收
//    @GetMapping("/testXml")
    @PostMapping("/testXml")
    public void testXml(@RequestBody Map<String, String> map) {

        String name = map.get("name");
        String sex = map.get("sex");
    }

}

