import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @file: FileChannelDemo.class
 * @author: Dusk
 * @since: 2019/4/26 21:40
 * @desc:
 */
public class FileChannelDemo {
    public static void main(String[] args) throws IOException {
        RandomAccessFile afile = new RandomAccessFile("data/nio-data.txt", "rw");
        FileChannel inChannel = afile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(45);
        // int bytesRead = inChannel.read(buf);
        String newData = "New String to write to file..." + System.currentTimeMillis();
        buf.clear();
        buf.put(newData.getBytes());
        buf.flip();
        while (buf.hasRemaining()){
            // inChannel.position(55)管道位置，指文件中光标位置，buf.position(4)buffer位置为缓冲区
            System.out.println(inChannel.size());
            //System.out.println(inChannel.position(inChannel.size()));
            // System.out.println(buf.position(4));
            inChannel.write(buf);

            System.out.println("a");
        }
        // force奖罚将通道里尚未写入磁盘里数据强制写入磁盘上
        /**
         * 出于性能方面的考虑，
         * 操作系统会将数据缓存在内存中，
         * 所以无法保证写入到FileChannel里的数据一定会即时写到磁盘上。
         * 要保证这一点，需要调用force()方法。
         */
        inChannel.force(true);
        inChannel.close();
    }
}
