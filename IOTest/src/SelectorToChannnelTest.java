import com.sun.org.apache.bcel.internal.generic.Select;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @file: SelectorToChannnelTest.class
 * @author: Dusk
 * @since: 2019/4/22 16:49
 * @desc:
 */
public class SelectorToChannnelTest {
    public static void main(String[] args) throws IOException {
        selectorDemo();
    }

    private static void selectorDemo() throws IOException {

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress("localhost", 8000));
        ssc.configureBlocking(false);

        Selector selector = Selector.open();
        // 注册channel，感兴趣为接收就绪
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer readBuff = ByteBuffer.allocate(1024);
        ByteBuffer writeBuff = ByteBuffer.allocate(128);
        writeBuff.put("received:薛瑞".getBytes());
        writeBuff.flip();
        selector.select();
        while (true){
            int readyNum = selector.select();
//            if (readyNum == 0){
//                continue;
//            }
            Set selectedKeys = selector.selectedKeys();
            Iterator it = selectedKeys.iterator();

            while(it.hasNext()){
                SelectionKey key = (SelectionKey) it.next();
                it.remove();
                if(key.isAcceptable()){
                    // 创建新连接，把连接注册到selector上，且该channel只对读操作感兴趣
                    SocketChannel socketChannel = ssc.accept(); //就绪
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if(key.isReadable()){
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    readBuff.clear();
                    socketChannel.read(readBuff);

                    readBuff.flip();
                    System.out.println("received:" + new String(readBuff.array()));
                    key.interestOps(SelectionKey.OP_WRITE);
                } else if(key.isWritable()){
                    writeBuff.rewind();
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    socketChannel.write(writeBuff);
                    key.interestOps(SelectionKey.OP_READ);
                }
            }
        }
    }
}
