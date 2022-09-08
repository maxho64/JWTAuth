package com.example.jwtauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecuredController {

    @GetMapping("/admin/get")
    public String getAdminInfo(){
        return "Hi, Admin";
    }

    @GetMapping("/user/get")
    public String getUserInfo(){
        return "Hi, User";
    }
}
