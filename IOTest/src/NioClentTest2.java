import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @file: NioClentTest2.class
 * @author: Dusk
 * @since: 2019/4/24 13:50
 * @desc:
 */
public class NioClentTest2 {
    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("localhost", 8000));

            ByteBuffer writBuffer = ByteBuffer.allocate(32);
            ByteBuffer readBuffer = ByteBuffer.allocate(32);

            writBuffer.put("谁最帅".getBytes());
            writBuffer.flip();

            while (true){
                // rewind()方法将position设回0 可以重读Buffer中的所有数据
                writBuffer.rewind();
                socketChannel.write(writBuffer);
                readBuffer.clear();
                socketChannel.read(readBuffer);
                System.out.println(new String(readBuffer.array()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
