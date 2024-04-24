package com.pyip.dao;

import com.pyip.pojo.User;

import java.util.List;

public interface IUser {
    // 查询所有
    public List<User> selectAll();

    // 根据条件查询
    public User selectOne(User user);
}
