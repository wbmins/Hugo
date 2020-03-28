package Nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @Classname TestNonBlocking
 * @Description non nio
 * @Date 2020/3/28 上午11:02
 * @Created by pluto
 */
public class TestNonBlocking {
    @Test
    public void client() throws IOException {
        //客户端
        //获取通道
        SocketChannel ssChannel = SocketChannel.open(
                new InetSocketAddress("127.0.0.1",8888));
        //切换非阻塞模式
        ssChannel.configureBlocking(false);
        //分配指定大小缓冲区
        ByteBuffer bf = ByteBuffer.allocate(1024);
        //发送数据
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String str = scanner.next();
            bf.put((new Date().toString()+str).getBytes());
            bf.flip();
            ssChannel.write(bf);
            bf.clear();
        }
//        bf.put(new Date().toString().getBytes());
//        bf.flip();
//        ssChannel.write(bf);
//        bf.clear();
        //关闭通道
        ssChannel.close();
    }
    @Test
    public void server() throws IOException {
        //服务端
        //获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        //q切换非阻塞
        ssChannel.configureBlocking(false);
        //绑定
        ssChannel.bind(new InetSocketAddress(8888));
        //获取选择器
        Selector selector = Selector.open();
        //通道注册到注册选择器,并指定监听事件
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);
        //轮巡式获取已经准备就绪的选择器
        while (selector.select() > 0){
            //获取选中当前选择器注册的选择键
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                //获取准备就绪的事件
                SelectionKey skey = iterator.next();
                //判断具体是什么事件准备就绪
                if(skey.isAcceptable()){
                    //若就收就绪,获取客户端连接
                    SocketChannel sChannel = ssChannel.accept();
                    //切换非阻塞模式
                    sChannel.configureBlocking(false);
                    //通道注册到选择器
                    sChannel.register(selector,SelectionKey.OP_READ);

                }else if(skey.isReadable()){
                    SocketChannel sChannel = (SocketChannel)skey.channel();
                    //读取数据
                    ByteBuffer bf = ByteBuffer.allocate(1024);
                    int len = 0;
                    while ((len = sChannel.read(bf)) > 0){
                        bf.flip();
                        System.out.println(new String(bf.array(),0,len));
                        bf.clear();
                    }
                }
            }
            //用完取消选择键
            iterator.remove();
        }

    }
}
