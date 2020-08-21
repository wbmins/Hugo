package Netty.GroupChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * 群聊 v1 服务端
 */
public class GroupChatServer {

    //定义属性
    //选择器
    private Selector selector;
    //通道
    private ServerSocketChannel serverSocketChannel;
    //端口
    private static final int PORT = 8888;

    //构造器
    public GroupChatServer() {
        try {
            //得到选择器
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            // 设置非阻塞模式
            serverSocketChannel.configureBlocking(false);
            //通道注册到selector
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //监听
    public void listen() {
        try {
            while (true) {
                int count = selector.select();
                //有事件需要处理
                if (count > 0) {
                    //遍历得到key集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();

                        //监听到acept
                        if (key.isAcceptable()) {
                            SocketChannel accept = serverSocketChannel.accept();
                            accept.configureBlocking(false);
                            //accept 注册到 selector
                            accept.register(selector, SelectionKey.OP_READ);
                            //提示信息
                            System.out.println(accept.getRemoteAddress() + "上线");
                        }
                        if (key.isReadable()) {
                            //通道处于可读状态
                            readData(key);
                        }
                        //当前的key删除掉
                        iterator.remove();
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //读取客户端消息
    private void readData(SelectionKey selectionKey) {
        SocketChannel channel = null;
        try {
            //得到channel
            channel = (SocketChannel) selectionKey.channel();
            //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            if (count > 0) {
                //输出
                String s = new String(buffer.array());
                System.out.println("from客户端" + s);
                //发给其他人
                sendOther(s, channel);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + "离线了");
                //取消注册
                selectionKey.channel();
                //关闭通道
                channel.close();
            } catch (IOException s) {

                s.printStackTrace();
            }
        }
    }

    //转发消息给其他客户端
    private void sendOther(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中......");
        //遍历key，排除自己
        for (SelectionKey key : selector.selectedKeys()) {
            //通过key去除channel
            Channel channel = key.channel();
            //排除自己
            if (channel instanceof SocketChannel && channel != self) {
                SocketChannel socketChannel = (SocketChannel) channel;
                //msg 存buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                socketChannel.write(buffer);

            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }

}
