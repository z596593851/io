package com.hxm.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class BlockCliTest {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 6666));
        SelectionKey key = socketChannel.register(selector, SelectionKey.OP_CONNECT);
        key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
        int readyKeys = selector.select();
        if(readyKeys>0){
            Iterable<SelectionKey> selectionKeys=selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isConnectable()) {
                    System.out.println("连接事件");
                    key.interestOps(key.interestOps() & ~SelectionKey.OP_CONNECT | SelectionKey.OP_READ);
                }
                if (selectionKey.isReadable()) {
                    System.out.println("读事件");
                }
                if (selectionKey.isWritable()) {
                    System.out.println("可写");
                }
            }
        }




    }


}
