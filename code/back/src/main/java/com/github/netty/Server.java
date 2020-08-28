package com.github.netty;

import com.github.netty.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Component
public class Server {

    public void run (InetSocketAddress address){
        //创建俩个线程组
        //初始化 1 个 EventLoopGroup
        EventLoopGroup boss = new NioEventLoopGroup(1);
        //默认 8 个　EventLoopGroup
        EventLoopGroup work = new NioEventLoopGroup();
        try{
            ServerBootstrap boot = new ServerBootstrap();
            boot.group(boss,work)
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
            ChannelFuture sync = boot.bind(address).sync();
            //监听关闭
            sync.channel().closeFuture().sync();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

}
