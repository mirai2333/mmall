package com.mmall.repository;

import com.mmall.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    //根据用户名查找用户
    Optional<User> findByUsername(String username);

    //根据用户名查询用户数量
    int countUserByUsername(String username);

    //根据用户邮箱查询用户数量
    int countUserByEmail(String email);

    //邮箱已有并且不是该id
    int countUserByEmailIsAndIdIsNot(String email, Integer id);
}
