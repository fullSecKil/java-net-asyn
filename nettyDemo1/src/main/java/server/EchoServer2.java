package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

public class EchoServer2 {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap server = new ServerBootstrap();
        server.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                // 积压
                .option(ChannelOption.SO_BACKLOG, 1024)
                // 保活
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // 无延迟
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        System.out.println("报告");
                        ch.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                        // 客户端触发操作
                        ch.pipeline().addLast(new EchoServerHandler());
                        ch.pipeline().addLast(new ByteArrayEncoder());
                    }
                });

        server.bind(8888).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(System.currentTimeMillis() + "端口绑定成功");
            } else {
                System.out.println("端口绑定失败！");
            }
        });

    }
}
