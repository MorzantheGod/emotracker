package com.innerman.emotracker.web.webcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by petrpopov on 08.03.14.
 */

@Controller
public class MainController {

    @RequestMapping("/")
    public String indexPage() {
        return "index";
    }
}
