package com.hxm.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用FileChannel配合缓冲区实现文件复制的功能
 */
public class test1 {
    public static void main(String[] args){
        FileChannel inChannel=null;
        FileChannel outChannel=null;
        try {
            FileInputStream fis=new FileInputStream("1.jpg");
            FileOutputStream fos=new FileOutputStream("2.jpg");
            inChannel=fis.getChannel();
            outChannel=fos.getChannel();

            ByteBuffer buf=ByteBuffer.allocate(1024);
            while (inChannel.read(buf)!=-1){
                buf.flip();//切换到读模式
                outChannel.write(buf);
                buf.clear();//切换回写模式

            }
        }catch (IOException e){
            System.out.println(e);
        }

    }

}
