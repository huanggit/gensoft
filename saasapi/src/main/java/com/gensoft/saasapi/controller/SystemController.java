package com.gensoft.saasapi.controller;

import com.gensoft.dao.user.User;
import com.gensoft.dao.user.UserRepository;
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
public class SystemController {


    @Value("${monitor.homeMessage}")
    private String homeMessage;

    @Cacheable("homeMessage")
    @RequestMapping("/")
    String monitorServerRun() {
        return homeMessage;
    }

}

