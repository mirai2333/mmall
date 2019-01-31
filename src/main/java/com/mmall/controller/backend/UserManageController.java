package com.mmall.controller.backend;

import com.mmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage/user")
public class UserManageController {
    private final UserService userService;
    @Autowired
    public UserManageController(UserService userService) {
        this.userService = userService;
    }

}
