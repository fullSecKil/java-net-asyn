import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * @file: NioPipeDemo.class
 * @author: Dusk
 * @since: 2019/5/3 11:34
 * @desc: nio 管道由两个线程之间单向数据连接。
 * Pipe有一个source通道和一个sink通道。数据会被写到sink通道，从source通道读取。
 */
public class NioPipeDemo {
    public static void main(String[] args) throws IOException {
        // 通过pipe.open方法打开管道
        Pipe pipe = Pipe.open();
        pipeCarried(pipe);
        pipeRead(pipe);
    }

    /**
     * 管道写入数据
     *
     * @param pipe
     */
    private static void pipeCarried(Pipe pipe) {
        try {
            // 向管道写数据，需要访问sink通道
            Pipe.SinkChannel sinkChannel = pipe.sink();
            String newData = "New String to write to file...";
            System.currentTimeMillis();
            ByteBuffer buf = ByteBuffer.allocate(48);
            buf.clear();
            buf.put(newData.getBytes());

            buf.flip();

            while (buf.hasRemaining()) {
                // sinkChannel.write方法将数据写入sinkChannel
                System.out.println("写入" + newData);
                sinkChannel.write(buf);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从管道读取数据
     *
     * @param pipe
     */
    private static void pipeRead(Pipe pipe) {
        Pipe.SourceChannel sourceChannel = pipe.source();
        ByteBuffer buffer = ByteBuffer.allocate(48);
        try {
            int bytesRead = sourceChannel.read(buffer);
            while (bytesRead != -1) {
                buffer.flip();
                System.out.println("读取");
                System.out.println(new String(buffer.array()));
                buffer.compact();
                bytesRead = sourceChannel.read(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
