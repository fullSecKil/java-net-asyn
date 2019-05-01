import java.io.*;
import java.net.Socket;

/**
 * @file: BioClientTest.class
 * @author: Dusk
 * @since: 2019/4/20 22:44
 * @desc:
 */
public class BioClientTest {
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 666);

        // 1 写数据给服务器
        OutputStream outputStream = socket.getOutputStream();

        PrintWriter writer = new PrintWriter(outputStream);
        writer.write("mmp\r\n");
        writer.flush();

        System.out.println("BioClientTest -> writer.flush(); 发送数据成功");

        // 2 读取服务器端返回的数据
        InputStream inputStream = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String lineStr = reader.readLine();
        System.out.println(lineStr.isEmpty());
        System.out.println("BioClientTest -> read.readLine() 服务器返回数据：" + lineStr);
    }
}
