package com.gensoft.saasapi.controller;

import com.gensoft.dao.user.User;
import com.gensoft.saasapi.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by alan on 16-5-20.
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    String register(@RequestBody User user) {
        userService.register(user);
        return "";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    String login() {
        return "";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "logout";
    }

    @RequestMapping(value = "/getVerificationCode", method = RequestMethod.POST)
    String getVerificationCode() {
        return "";
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    String resetPassword() {
        return "";
    }
}
