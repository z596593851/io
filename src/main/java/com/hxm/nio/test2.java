package com.hxm.nio;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 使用内存映射文件的方式实现文件复制的功能(直接操作缓冲区)
 */
public class test2 {
    public static void main(String[] args){
        try {
            //jdk1.7后通过静态方法.open()获取通道
            FileChannel inChannel=FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
            FileChannel outChannel=FileChannel.open(Paths.get("3.jpg"), StandardOpenOption.WRITE,StandardOpenOption.CREATE);
            inChannel.transferTo(0,inChannel.size(),outChannel);
            inChannel.close();
            outChannel.close();
        }catch (IOException e){
            System.out.println(e);
        }


    }

}
