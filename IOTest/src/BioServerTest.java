import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @file: BioServerTest.class
 * @author: Dusk
 * @since: 2019/4/20 22:33
 * @desc:
 */
public class BioServerTest {

    private static boolean isRun = true;

    public static boolean isIsRun() {
        return isRun;
    }

    public static void setIsRun(boolean isRun) {
        BioServerTest.isRun = isRun;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();

        // 绑定端口
        serverSocket.bind(new InetSocketAddress("127.0.0.1", 666));

        while(isRun){
            Socket socket = serverSocket.accept();  // 等待用户连接
            System.out.println("BioServerTest -> serverSocket.accept()");
            new Thread(()->{
                try {
                    // 拿到客户端的请求数据
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String lineStr = reader.readLine();
                    System.out.println("BioServerTest -> read.readLine() 读到数据：" + lineStr);

                    // 处理完数据，把数据写回客户端
                    String result = "nidiao";
                    if("mmp".equals(lineStr)){
                        result = "cnm";
                        BioServerTest.setIsRun(false);
                    }
                    OutputStream outputStream = socket.getOutputStream();
                    PrintWriter printWriter = new PrintWriter(outputStream, true);
                    // printWriter.write(result);
                    System.out.println(result);
                    printWriter.println(result);
                    // printWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        serverSocket.close();
    }
}
