import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * @file: TinyHttpd.class
 * @author: Dusk
 * @since: 2019/4/24 17:46
 * @desc:
 */
public class TinyHttpd {

    private static final int DEFAULT_PORT = 8080;
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    private static final String INDEX_PAGE = "index.html";
    private static final String STATIC_RESOURCE_DIR = "static";
    private static final String META_RESOURCE_DIR_PREFIX = "/meta/";
    private static final String KEY_VALUE_SEPARATOR = ":";
    private static final String CRLF = "\r\n";

    private int port;

    public TinyHttpd() {
        this(DEFAULT_PORT);
    }

    public TinyHttpd(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress("localhost", this.port));
        ssc.configureBlocking(false);

        // 创建 Selector
        Selector selector = Selector.open();

        // 注册事件
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int readNum = selector.select();
            if (readNum == 0) {
                continue;
            }

            Set selectedKeys = selector.selectedKeys();
            Iterator it = selectedKeys.iterator();
            while (it.hasNext()) {
                SelectionKey selectionKey = (SelectionKey) it.next();
                it.remove();

                if (selectionKey.isAcceptable()) {
                    //  创建新的连接，并且把连接注册到selector上
                    SocketChannel socketChannel = ssc.accept(); // 等待用户连接，阻塞
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    // 处理请求
                    request(selectionKey);
                    // 兴趣
                    selectionKey.interestOps(SelectionKey.OP_WRITE);
                } else if (selectionKey.isWritable()) {
                    response(selectionKey);
                }
            }
        }
    }

    private void request(SelectionKey selectionKey) throws IOException {
        // 从通道中读取请求头数据
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        ByteBuffer buffer = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);
        channel.read(buffer);

        buffer.flip();
        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes);
        String headerStr = new String(bytes);

        // 解析请求头
        Headers headers = parseHeader(headerStr);
        // 将请求头对象放入 selectionKey中
        selectionKey.attach(Optional.of(headers));

    }

    private Headers parseHeader(String headerStr) {
        if (Objects.isNull(headerStr) || headerStr.isEmpty()) {
            System.out.println("抛出异常");
        }
        // 解析请求头第一行
        int index = headerStr.indexOf(CRLF);
        if (index == -1) {
            System.out.println("抛出异常");
        }

        Headers headers = new Headers();
        String firstLine = headerStr.substring(0, index);
        String[] parts = firstLine.split(" ");

        /**
         * 请求头的第一行必须由三部分构成， 分为method path version
         * 比如：
         *      GET /index.html HTTP/1.1
         */
        if (parts.length < 3) {
            System.out.println("抛出异常");
        }
        headers.setMethod(parts[0]);
        headers.setPath(parts[1]);
        headers.setVersion(parts[2]);

        // 解析请求头属于部分
        parts = headerStr.split(CRLF);
        for (String part :
                parts) {
            index = part.indexOf(KEY_VALUE_SEPARATOR);
            if (index == -1) {
                continue;
            }
            String key = part.substring(0, index);
            if (index + 1 >= part.length()) {
                headers.setHeaderMap(key, "");
                continue;
            }
            String value = part.substring(index + 1);
            headers.setHeaderMap(key, value);
        }
        return headers;
    }

    private void response(SelectionKey selectionKey) throws IOException {

        SocketChannel channel = (SocketChannel) selectionKey.channel();
        Optional op = (Optional) selectionKey.attachment();

        if (!op.isPresent()) {
            // handleBadRequest(channel);
        }

        String ip = channel.getRemoteAddress().toString().replace("/", "");
        Headers handers = (Headers) op.get();
        if (handers.getPath().startsWith(META_RESOURCE_DIR_PREFIX)) {
            handleForbidden(channel);
            channel.close();
            System.out.println();
            return;
        }

        handleOK(channel, handers.getPath());

    }

    private void handleOK(SocketChannel channel, String path) {
        // ResponseHeaders headers = new ResponseHeaders(OK.getCode());

    }

    private void handleForbidden(SocketChannel channel) {

    }
}
