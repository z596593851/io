package com.hxm.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2019/2/27.
 */
public class main {
    public static void main(String[] args) {
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Invoker invoker=InvokerHolder.getInvoker((short) 1,(short) 1);
        invoker.invoke(null);

    }
}
