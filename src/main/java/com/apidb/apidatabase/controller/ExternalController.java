package com.apidb.apidatabase.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExternalController {

    @GetMapping("/api/external/data")
    public String getData() {
        return "API KEY WORKING ✅";
    }
}