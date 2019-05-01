import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

/**
 * @file: FileChannelTest.class
 * @author: Dusk
 * @since: 2019/4/22 12:37
 * @desc:
 */
public class FileChannelTest {

    public static void main(String[] args) throws IOException {
        // 随机访问文件对象
        RandomAccessFile aFile = new RandomAccessFile("data/nio-data.txt", "rw");
        FileChannel inChannel = aFile.getChannel();

        // Buffer 工作原理，需要熟悉三个属性 capacity(容量)、position(位置)、limit
        // 创建容量为48字节的缓冲区
        ByteBuffer buffer =  ByteBuffer.allocate(16);
//        int bytesRead = inChannel.read(buffer);     // 从管道读入缓冲区
//        byte[] a = new byte[]{'x', 'u', 'e', 'r', 'u', 'i'};
//        // buffer.put(a);
//        while (bytesRead != -1){
//            System.out.println("read" + bytesRead);
//            // 一旦要读取数据，需要通过flip()方法将Buffer从写模式切换到读模式
//            buffer.flip();
//            // hasRemaining有剩余
//            while (buffer.hasRemaining()){
//                System.out.print((char) buffer.get());
//            }
//           // Buffer.rewind()将position设置0，可以重读buffer中所有数据
//            // buffer.rewind();
//            // mark, reset mark方法标记流中position的地址， reset恢复到mark地址
//            // buffer.mark();
//            // buffer.reset();
//
//            // 一旦读完了所有的数据，就需要清空缓冲区，让它可以再次被写入
//            buffer.compact();
//            bytesRead = inChannel.read(buffer);
//        }
        ByteBuffer header =  ByteBuffer.allocate(5);
        ByteBuffer body =  ByteBuffer.allocate(50);
        ByteBuffer[] bufferArray = {header, body};
        inChannel.read(bufferArray);
        header.flip();
        body.flip();

        while (header.hasRemaining()){
            System.out.println((char) header.get());
        }
        // Arrays.stream(bufferArray).filter(Buffer::hasRemaining).forEach(b->System.out.println((char)b.get()));
        // System.out.println(bufferArray[1].get());
        aFile.close();
    }
}
