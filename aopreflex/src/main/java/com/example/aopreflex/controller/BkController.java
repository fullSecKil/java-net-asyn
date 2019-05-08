package com.example.aopreflex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @file: BkController.class
 * @author: Dusk
 * @since: 2019/5/7 1:11
 * @desc:
 */
@RestController
public class BkController {
    @PostMapping("/test1")
    public String bbCheckSign(@RequestParam String arg0, @RequestParam String arg1, @RequestParam String arg2) {
        return "ok";
    }

//    @GetMapping("/test2")
//    public String test() {
//        return "test2 ok";
//    }

}
