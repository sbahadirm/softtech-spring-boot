package com.softtech.softtechspringboot.learning.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/tests")
public class TestController {

    @RequestMapping(method = RequestMethod.GET, path = "/hello")
    public void sayHello(){
        System.out.println("hello");
    }

    @GetMapping()
    public String sayHelloWorld(){

        String helloWorld = "hello world!";

        System.out.println("text: " + helloWorld);

        return helloWorld;
    }

    @PostMapping
    public void save(){
        System.out.println("saved!");
    }

    @GetMapping("/sayHelle/{person}")
    public String sayHello(@PathVariable String person){

        System.out.println(person);

        return "Hello " + person;
    }

    @GetMapping("/sayHello")
    public String sayHelloWithParam(@RequestParam String name, @RequestParam String surname){

        System.out.println(name);

        return "Hello " + name + " " + surname;
    }
}
