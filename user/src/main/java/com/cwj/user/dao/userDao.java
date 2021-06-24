package com.cwj.user.dao;


import com.cwj.user.domain.user;

import java.util.List;

/*
用户表的增删改查
 */
public interface userDao {
    /**
     * 向user表添加一位用户
     *
     * @param u
     */
    void insertUser(user u);

    /**
     * 根据传入的用户名和密码 查询是否有该用户
     *
     * @param u
     */
    user findUser(user u);

    /**
     * 查询所有用户信息 存储到redis中
     * @return
     */
     List<user> findAll();
}
