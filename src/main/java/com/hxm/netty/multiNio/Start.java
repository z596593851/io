package com.hxm.netty.multiNio;

import com.hxm.netty.multiNio.pool.NioSelectorRunnablePool;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * 启动函数
 * @author -琴兽-
 *
 */
public class Start {

	public static void main(String[] args) {

		
		//初始化线程
		//将所有boss线程和worker线程全部放入对应的两个线程池中，并启动线程池，所有线程阻塞在Selector.select,等待selector的注册
		NioSelectorRunnablePool nioSelectorRunnablePool = new NioSelectorRunnablePool(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		
		//获取服务类
		ServerBootstrap bootstrap = new ServerBootstrap(nioSelectorRunnablePool);
		
		//绑定端口
		/*
		boss和worker的所有线程都阻塞在select(selector)
		nextBoss.registerAcceptChannelTask boss向queue推送一个注册accept的task
		由于boss先注册了，调用selector.wakeup()激活了它阻塞着的select(selector)
		boss向下执行，从queue取出注册accept的task
		boss向下执行process，但是由于selector是被强行唤醒的，没有selectedKeys，所以process直接返回，阻塞在select(selector)


		**新客户端连入，boss的select(selector)被激活，worker的不被激活
		boss向下执行process，向worker的queue推送注册read的task
		由于boss向worker推送task时触发了worker的selector.wakeup()，worker向下执行
		worker向下执行，从queue取出注册read的task完成注册，继续向下执行process时由于是被强制唤醒，selectedKeys为空
		所以直接返回，回到selector阻塞

		**客户端发消息，worker的select(selector)被激活，处理消息，boss继续阻塞等待下一次新客户端的连接

		综上，boss所在线程池只负责新客户端的连接，worker只负责已连接成功的客户端的会话，且worker线程池所有线程抢占式
		争夺select(selector)的激活权利

		更绝妙的是，worker的selector只绑定ServerSocketChannel，boss的selector只绑定SocketChannel，两线程池之间互不干扰，
		之间通过
		而且SocketChannel是由ServerSocketChannel accept()而来





		 */
		bootstrap.bind(new InetSocketAddress(10101));
		
		System.out.println("start");
	}

}
