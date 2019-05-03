import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * @file: DatagramChannelDemo.class
 * @author: Dusk
 * @since: 2019/5/2 20:58
 * @desc: Java NIO中的DatagramChannel是一个能收发UDP包的通道。
 *  因为UDP是无连接的网络协议，所以不能像其它通道那样读取和写入。它发送和接收的是数据包。
 */
public class DatagramChannelDemo {
    public static void main(String[] args) {
        testDatagramChanne();
    }

    private static void testDatagramChanne() {
        try {
            DatagramChannel channel = DatagramChannel.open();
            channel.socket().bind(new InetSocketAddress(9999));
            ByteBuffer buf = ByteBuffer.allocate(48);
            buf.clear();
            // 通过receive()方法从DatagramChannel接收数据
            /**
             * receive()方法会将接收到的数据包内容复制到指定的Buffer.
             * 如果Buffer容不下收到的数据，多出的数据将被丢弃。
             */
            channel.receive(buf);
            buf.flip();
            while (buf.hasRemaining()){
                System.out.print((char) buf.get());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
