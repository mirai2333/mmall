package com.mmall.controller.portal;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ServerResponse<String> registerUser(@RequestBody @Valid User user,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ServerResponse.createByErrorMsg(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return userService.registerUser(user);
    }

    @GetMapping("/getUserInfo")
    public ServerResponse<User> getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object user = (auth != null) ? auth.getPrincipal() : null;
        if (user instanceof User) {
            return userService.getUserInfo(((User) user).getId());
        }
        return ServerResponse.createByErrorMsg("会话失效");
    }

    @GetMapping("/getForgetQuestion")
    public ServerResponse<String> getForgetQuestion(@RequestParam String username) {
        return userService.getForgetQuestion(username);
    }

    @GetMapping("/checkForgetAnswer")
    public ServerResponse<String> checkForgetAnswer(@RequestParam String username,
                                                    @RequestParam String answer) {
        return userService.checkForgetAnswer(username, answer);
    }

    @PutMapping("/AnswerResetPassword")
    public ServerResponse<String> AnswerResetPassword(@RequestParam String username,
                                                      @RequestParam String newPassword,
                                                      @RequestParam String userToken) {
        if (StringUtils.isEmpty(newPassword)) {
            return ServerResponse.createByErrorMsg("缺少密码");
        }
        if (StringUtils.isEmpty(userToken)) {
            return ServerResponse.createByErrorMsg("缺少token");
        }
        return userService.AnswerResetPassword(username, newPassword, userToken);
    }
    @PutMapping("/resetPassword")
    public ServerResponse<String> resetPassword(@RequestParam String oldPassword,
                                                @RequestParam String newPassword) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object user = (auth != null) ? auth.getPrincipal() : null;
        if (user instanceof User) {
            return userService.resetPassword(((User) user).getId(), oldPassword, newPassword);
        }
        return ServerResponse.createByErrorMsg("会话失效");
    }
    @PutMapping("/updateUserInfo")
    public ServerResponse<User> updateUserInfo(@Valid User user){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object currentUser = (auth != null) ? auth.getPrincipal() : null;
        if (currentUser instanceof User) {
            //获取session中的用户id
            user.setId(((User) currentUser).getId());
            return userService.updateUserInfo(user);

        }
        return ServerResponse.createByErrorMsg("会话失效");
    }

    @GetMapping("/test")
    public String test(HttpSession session) {
        //session中的属性名称是（应该可以通过一些内置方法拿到）：SPRING_SECURITY_CONTEXT
        return "Hello";
    }
}
