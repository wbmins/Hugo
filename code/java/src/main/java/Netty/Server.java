package main.java.Netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Server {

    //端口
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {

        //创建俩个线程组
        EventLoopGroup boss = new NioEventLoopGroup(1);
        //默认8个EventLoop
        EventLoopGroup work = new NioEventLoopGroup();
        try {
            ServerBootstrap boot = new ServerBootstrap();
            boot.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            //获取pipeline
                            ChannelPipeline pipeline = sc.pipeline();
                            //向pipeline加入解码器
                            pipeline.addLast("decoder", new StringDecoder());
                            //向pipeline加入编码器
                            pipeline.addLast("encoder", new StringEncoder());
                            //加入自己处理handler
                            pipeline.addLast(new ServerHandler());
                        }
                    });
            System.out.println("netty starter");
            ChannelFuture sync = boot.bind(port).sync();
            //监听关闭
            sync.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws InterruptedException {

        new Server(7000).run();

    }

}
