package com.innerman.emotracker.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by petrpopov on 08.03.14.
 */

@Controller
public class MainController {

    @RequestMapping(value = {"/", "/index"})
    public String indexPage() {
        return "index";
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }
}
