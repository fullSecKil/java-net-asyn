import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @file: SocketChannelDemo.class
 * @author: Dusk
 * @since: 2019/4/26 23:15
 * @desc: java nio 中SocketChannel是一个连接到tcp网络套接字的通道可以通过以下两个方式创建
 * 1、打开一个SocketChannel并连接到互联网上某台服务器
 * 2、一个连接到达ServerSocketChannel时，会创建一个SocketChannel
 */
public class SocketChannelDemo2 {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("localhost", 9999));

        String newData = "New String to write to file..." + System.currentTimeMillis();
        // 分配48字节的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(48);
        buffer.clear();
        // int bytesRead = socketChannel.read(buffer); 读到buffer
        buffer.put(newData.getBytes());
        // 反转
        buffer.flip();

        while (buffer.hasRemaining()){
            // wirite()方法无法保证能写多少字节到socketchannel所以我们重复调用write直到buffer没有要写的字节为止
            socketChannel.write(buffer);
        }
        System.out.println(buffer.array());
    }
}
