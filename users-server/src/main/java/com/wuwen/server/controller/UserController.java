package com.wuwen.server.controller;

import com.wuwen.api.util.RpcResult;
import com.wuwen.server.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author wuwen
 */
@RestController
@RequestMapping(value = "/customer")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping(value = "/store")
    @ResponseBody
    public RpcResult store() {
        return RpcResult.buildSuccess(null);
    }

    @GetMapping(value = "/find")
    @ResponseBody
    public RpcResult show() {
        return RpcResult.buildSuccess(null);
    }
}
