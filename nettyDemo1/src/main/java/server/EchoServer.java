package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

/**
 * 例子来自此网站，java的netty通讯
 * https://www.cnblogs.com/jtlgb/p/8757587.html
 */
public class EchoServer {

    private final int prot;

    public EchoServer(int prot) {
        this.prot = prot;
    }

    public void start() throws InterruptedException {
        // 事件循环组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 服务引导
            ServerBootstrap sb = new ServerBootstrap();
            // 选择管道
            sb.option(ChannelOption.SO_BACKLOG, 1024);
            // 绑定线程池
            sb.group(group, bossGroup)
                    // 指定使用的channel
                    .channel(NioServerSocketChannel.class)
                    // 绑定监听的端口
                    .localAddress(this.prot)
                    // 绑定客户端连接时候操作
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("报告");
                            System.out.println("信息：有一客户端接到本服务端");
                            System.out.println("IP：" + ch.localAddress().getHostName());
                            System.out.println("Port：" + ch.localAddress().getPort());
                            System.out.println("报告完毕");

                            ch.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                            // 客户端触发操作
                            ch.pipeline().addLast(new EchoServerHandler());
                            ch.pipeline().addLast(new ByteArrayEncoder());
                        }
                    });
            // 服务器异步创建绑定
            ChannelFuture cf = sb.bind().sync();
            System.out.println(EchoServer.class + "启动正在监听：" + cf.channel().localAddress());
            // 关闭服务器通道
            cf.channel().closeFuture().sync();
        }finally {
            // 释放线程池资源
            group.shutdownGracefully().sync();
            bossGroup.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 启动
        new EchoServer(8888).start();
    }

}
