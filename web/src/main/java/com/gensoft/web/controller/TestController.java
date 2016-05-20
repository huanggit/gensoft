package com.gensoft.web.controller;

import com.gensoft.web.domain.User;
import com.gensoft.web.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by alan on 16-5-18.
 */
@RestController
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @Value("${monitor.homeMessage}")
    private String homeMessage;

    @Cacheable("homeMessage")
    @RequestMapping("/")
    String monitorServerRun() {
        return homeMessage;
    }

    @RequestMapping("/testSave")
    String testsave() {
        User user = new User("test", "test@test");
        userRepository.save(user);
        return "success";
    }


    @RequestMapping("/testGet/{userId}")
    User testget(@PathVariable Long userId) {
        User user = userRepository.xx(userId);
        return user;
    }
}

