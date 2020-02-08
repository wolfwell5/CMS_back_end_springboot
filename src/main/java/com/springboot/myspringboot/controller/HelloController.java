package com.springboot.myspringboot.controller;

import com.springboot.myspringboot.entity.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/test")
public class HelloController {
// commit

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

}

