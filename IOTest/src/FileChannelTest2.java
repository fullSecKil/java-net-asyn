import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @file: FileChannelTest2.class
 * @author: Dusk
 * @since: 2019/4/22 16:21
 * @desc:
 */
public class FileChannelTest2 {

    public static void main(String[] args) throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile("data/fromFile.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("data/toFile.txt", "rw");
        FileChannel toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();

        fromChannel.transferTo(position, count, toChannel);
    }
}
