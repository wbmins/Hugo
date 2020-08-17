package Netty.GroupChat;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.NetPermission;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 群聊 v1 客户端
 */
public class GroupChatClient {

    //选择器
    private final Selector selector;
    //通道
    private final SocketChannel socketChannel;
    private final String userName;

    public GroupChatClient() throws IOException {
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
        userName = socketChannel.getLocalAddress().toString().substring(1);
        socketChannel.configureBlocking(false);
        System.out.println(userName + "is ok ......");

    }

    //发消息
    public void send(String msg) {
        msg = userName + "say:" + msg;
        try {
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //读消息
    public void read() {
        try {
            int count = selector.select();
            if (count > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    if (next.isReadable()) {
                        //得到通道
                        SocketChannel channel = (SocketChannel) next.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        channel.read(buffer);
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                }
            } else {
                System.out.println("没有可用通道");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        GroupChatClient groupChatClient = new GroupChatClient();
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.currentThread().sleep(3000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            groupChatClient.send(s);
        }
    }


}
