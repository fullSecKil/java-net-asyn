import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * @file: DatagramChannelClientDemo.class
 * @author: Dusk
 * @since: 2019/5/2 21:15
 * @desc: DatagramChannel发送数据
 */
public class DatagramChannelClientDemo {
    public static void main(String[] args) {
        udpsend();
    }

    private static void udpsend() {

        String newData = "New String to write to file...";
        ByteBuffer buffer = ByteBuffer.allocate(48);
        buffer.clear();
        buffer.put(newData.getBytes());
        buffer.flip();
        try {
            DatagramChannel channel = DatagramChannel.open();
            int bytesSent = channel.send(buffer, new InetSocketAddress("localhost", 9999));
            System.out.println(bytesSent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
