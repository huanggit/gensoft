package com.gensoft.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;


/**
 * Created by alan on 16-5-20.
 */
@Controller
public class TestController {

    @RequestMapping("/test")
    public String testServerRun(Model model){
        model.addAttribute("time", new Date());
        model.addAttribute("message", "hello world!");
        return "testServerRun";
    }
}
