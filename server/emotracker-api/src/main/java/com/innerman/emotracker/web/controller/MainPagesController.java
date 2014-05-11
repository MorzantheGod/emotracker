package com.innerman.emotracker.web.controller;

import com.innerman.emotracker.core.model.UserEntity;
import com.innerman.emotracker.web.utils.UserContextHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Created by petrpopov on 08.03.14.
 */

@Controller("mainWebController")
public class MainPagesController {

    @Autowired
    private UserContextHandler userContextHandler;

    @RequestMapping(value = {"/", "/index"})
    public String indexPage() {
        return "index";
    }

    @RequestMapping(value = "/dataevent/{dataEventId}")
    public String dataEventPage(@PathVariable String dataEventId, Model model) {

        model.addAttribute("dataEventId", dataEventId);
        return "dataevent";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout) {

        UserEntity entity = userContextHandler.currentContextUser();
        if( entity != null )
            return new ModelAndView(new RedirectView("index"));

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");

        return model;

    }
}
