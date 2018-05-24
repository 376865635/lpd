package com.hob.lpd.film.controller;

import com.hob.lpd.PropertyCommonDB;
import com.hob.lpd.film.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class test {

    @Autowired
    private PropertyCommonDB propertyCommonDB;
    @Autowired
    private TestService testService;


    @RequestMapping("/test")
    public String test(){
        testService.search();
        return "1";
    }
}
