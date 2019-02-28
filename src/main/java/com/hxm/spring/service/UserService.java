package com.hxm.spring.service;

import com.hxm.spring.annotion.SocketCmd;
import com.hxm.spring.annotion.SocketModule;

/**
 * Created by Administrator on 2019/2/27.
 */
@SocketModule(module = 1)
public interface UserService {

    @SocketCmd(cmd = 1)
    public void login();

    @SocketCmd(cmd = 2)
    public void getInfo();
}
