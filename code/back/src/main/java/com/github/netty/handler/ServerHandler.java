package com.github.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ServerHandler extends SimpleChannelInboundHandler<String> {

    //定义 channel 组管理所有的channel
    //GlobalEventExecutor.INSTANCE 全局事件执行器，是一个单例
    private static final ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    //事件格式化
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public String getNow() {
        return sdf.format(Calendar.getInstance().getTime());
    }

    //连接建立，第一个被执行
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();
        //将该客户加入聊天的信息发动给其他在线客户端
        //writeAndFlush 遍历 group 并发动消息
        group.writeAndFlush("[客户端]" + getNow() + channel.remoteAddress() + "加入聊天");
        //当前channel加入channel组中
        group.add(channel);

    }

    //表示 channel 处于活动状态
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println(getNow() + ctx.channel().remoteAddress() + " 上线了");

    }

    //表示 channel 处于非活动状态
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        System.out.println(getNow() + ctx.channel().remoteAddress() + " 离线了");

    }

    //表示 channel 处于离开了
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();
        group.writeAndFlush("[客户端] " + getNow() + channel.remoteAddress() + "离开了\n");
        System.out.println("group.size: " + getNow() + group.size());

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        Channel channel = ctx.channel();
        //遍历group，更具不同的情况回送不同的消息
        group.forEach(item -> {
            if (channel != item) {
                item.writeAndFlush("[客户] " + getNow() + channel.remoteAddress() + " : " + msg);
            } else {
                item.writeAndFlush("[自己] " + getNow() + channel.remoteAddress() + " : " + msg);
            }
        });

    }

    //发生异常的处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭通道
        ctx.close();
    }
}
