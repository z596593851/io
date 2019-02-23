package com.hxm.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;

public class NoBlockServer {

    public static void main(String[] args) throws IOException {

        // 1.获取通道
        ServerSocketChannel server = ServerSocketChannel.open();

        // 2.切换成非阻塞模式
        server.configureBlocking(false);

        // 3. 绑定连接
        server.bind(new InetSocketAddress(6666));

        // 4. 获取选择器
        Selector selector = Selector.open();

        // 4.1将通道注册到选择器上，指定接收“监听通道”事件
        server.register(selector, SelectionKey.OP_ACCEPT);

        // 5. 轮训地获取选择器上已“就绪”的事件--->只要select()>0，说明已就绪
        while (selector.select() > 0) {
            // 6. 获取当前选择器所有注册的“选择键”(已就绪的监听事件)
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            // 7. 获取已“就绪”的事件，(不同的事件做不同的事)
            while (iterator.hasNext()) {

                SelectionKey selectionKey = iterator.next();

                // 接收事件就绪
                if (selectionKey.isValid()&&selectionKey.isAcceptable()) {

                    // 8. 获取客户端的链接
                    SocketChannel client = server.accept();

                    // 8.1 切换成非阻塞状态
                    client.configureBlocking(false);

                    // 8.2 注册到选择器上-->拿到客户端的连接为了读取通道的数据(监听读就绪事件)
                    //为了可以接受客户端消息，需要给通道设置读的权限
                    client.register(selector, SelectionKey.OP_READ);

                } else if (selectionKey.isValid()&&selectionKey.isReadable()) { // 读事件就绪


                    SocketChannel client = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    FileChannel outChannel = FileChannel.open(Paths.get("15.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

                    try {
                        while (client.read(buffer) > 0) {
                            buffer.flip();
                            outChannel.write(buffer);
                            buffer.clear();
                        }
                        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                        writeBuffer.put("the img is received".getBytes());
                        writeBuffer.flip();
                        client.write(writeBuffer);

                    }catch (IOException e){
                        System.out.println("客户端关闭");
                        selectionKey.cancel();
                    }



//                    while (client.read(buffer) > 0) {
//
//                    }

                }
                // 10. 取消选择键(已经处理过的事件，就应该取消掉了)
                iterator.remove();
            }
        }

    }
}


