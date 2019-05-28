package cilent;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class EchoClient {

    private final  String host;
    private final int port;

    public EchoClient() {
        this(0);
    }

    public EchoClient(int port) {
        this("localhost", port);
    }

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        // 注册线程池
        b.group(group)
                // 用NioSocketChannel作为连接用的channel类
                .channel(NioSocketChannel.class)
                // 绑定连接端口和host信息
                .remoteAddress(new InetSocketAddress(this.host, this.port))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        System.out.println("正在连接中...");
                        ch.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                        ch.pipeline().addLast(new EchoClientHandler());
                        ch.pipeline().addLast(new ByteArrayEncoder());
                        ch.pipeline().addLast(new ChunkedWriteHandler());
                    }
                });
        // 异步连接服务器
        ChannelFuture cf = b.connect().sync();
        System.out.println("服务端连接成功..");

        // 异步等待关闭连接channel
        cf.channel().closeFuture().sync();
        System.out.println("连接已关闭..");
        // 释放线程池资源
        group.shutdownGracefully().sync();
    }

    public static void main(String[] args) throws InterruptedException {
        new EchoClient(8888).start();
    }
}
