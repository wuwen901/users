package com.wuwen.server.controller;

import com.wuwen.server.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuwen
 */
@Slf4j
@RestController
@RequestMapping("xxy")
public class LoginController {

    @Autowired
    private UserService customerService;


}
