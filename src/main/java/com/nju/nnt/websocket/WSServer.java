package com.nju.nnt.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;



public class WSServer {

    /**
     * 单例静态内部类
     * @author asus
     *
     */
    static final WSServer instance = new WSServer();

    public static WSServer getInstance(){
        return instance;
    }
    private EventLoopGroup mainGroup;
    private EventLoopGroup subGroup;
    private ServerBootstrap server;
    private ChannelFuture future;
    public WSServer() {
        mainGroup = new NioEventLoopGroup();
        subGroup = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(mainGroup, subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WSServerInitializer()); //添加⾃定义初始化处理器

    }
    public void start(){
        future = this.server.bind(8088);
        System.err.println("netty 服务端启动完毕 .....");
    }
}
