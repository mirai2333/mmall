package com.mmall.service;

import com.mmall.common.Const;
import com.mmall.common.RedisUtil;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,RedisUtil redisUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.redisUtil = redisUtil;
    }

    public ServerResponse<String> registerUser(User user) {
        int usernameCount = userRepository.countUserByUsername(user.getUsername());
        if (usernameCount > 0) {
            return ServerResponse.createByErrorMsg("用户名已存在");
        }
        int emailCount = userRepository.countUserByEmail(user.getEmail());
        if (emailCount > 0) {
            return ServerResponse.createByErrorMsg("邮箱已存在");
        }
        user.setRole(Const.Role.ROLE_CUSTOMER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userRepository.save(user);
        return ServerResponse.createBySuccessMsg("注册成功");
    }

    public ServerResponse<User> getUserInfo(Integer id){
        return userRepository.findById(id)
                .map(ServerResponse::createBySuccess).orElseGet(() -> ServerResponse.createByErrorMsg("用户不存在"));
    }

    public ServerResponse<String> getForgetQuestion(String username) {
        ServerResponse<String> result = userRepository.findByUsername(username)
                .map(user -> ServerResponse.createBySuccess(user.getQuestion()))
                .orElse(ServerResponse.createByErrorMsg("用户不存在"));
        if (result.isSuccess() && StringUtils.isEmpty(result.getData())) {
            return ServerResponse.createByErrorMsg("密保问题为空");
        }
        return result;
    }

    public ServerResponse<String> checkForgetAnswer(String username, String answer) {
        String realAnswer = userRepository.findByUsername(username).map(User::getAnswer).orElse(null);
        if (StringUtils.isEmpty(realAnswer) || !realAnswer.equals(answer)) {
            return ServerResponse.createByErrorMsg("密保答案错误");
        }
        String userToken = UUID.randomUUID().toString();
        if (!redisUtil.setUserToken(username, userToken)) {
            return ServerResponse.createByErrorMsg("设置token失败，请稍后重试");
        }
        return ServerResponse.createBySuccessMsg(userToken);
    }

    public ServerResponse<String> AnswerResetPassword(String username, String newPassword, String userToken) {
        Optional<User> userOptional = userRepository.findByUsername(username)
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setUpdateTime(new Date());
                    return user;
                });
        if (!userOptional.isPresent()) {
            return ServerResponse.createByErrorMsg("用户不存在");
        }
        if (!userToken.equals(redisUtil.getUserToken(username))) {
            return ServerResponse.createByErrorMsg("token无效或过期");
        }
        userRepository.save(userOptional.get());
        return ServerResponse.createBySuccess();
    }

    public ServerResponse<String> resetPassword(Integer userID, String oldPassword, String newPassword) {
        Optional<User> userOptional = userRepository.findById(userID).map(user -> {
            user.setUpdateTime(new Date());
            return user;
        });
        if (!userOptional.isPresent()) {
            return ServerResponse.createByErrorMsg("用户不存在");
        }
        User user = userOptional.get();
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return ServerResponse.createBySuccessMsg("原密码错误");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return ServerResponse.createBySuccess();
    }

    public ServerResponse<User> updateUserInfo(User user) {
        Optional<User> uOptional = userRepository.findById(user.getId());
        if (!uOptional.isPresent()) {
            return ServerResponse.createByErrorMsg("用户不存在");
        }
        User u = uOptional.get();
        if (!StringUtils.isEmpty(user.getEmail())) {
            if (userRepository.countUserByEmailIsAndIdIsNot(user.getEmail(), user.getId()) > 0) {
                return ServerResponse.createByErrorMsg("邮箱已被占用");
            }
        }
        u.setEmail(user.getEmail());
        u.setPhone(user.getPhone());
        u.setQuestion(user.getQuestion());
        u.setAnswer(user.getAnswer());
        u.setUpdateTime(new Date());
        User newUser = userRepository.save(u);
        return newUser != null ? ServerResponse.createBySuccess(newUser) : ServerResponse.createByErrorMsg("更新用户失败");
    }
}
