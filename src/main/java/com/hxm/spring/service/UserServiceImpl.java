package com.hxm.spring.service;

import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/2/27.
 */

@Component
public class UserServiceImpl implements UserService {
    @Override
    public void login() {
        System.out.println("login");
    }

    @Override
    public void getInfo() {
        System.out.println("get info");

    }
}
