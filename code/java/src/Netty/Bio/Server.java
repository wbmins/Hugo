package Netty.Bio;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * bio 是事件驱动的，基于线程的
 * 同步阻塞
 */
public class Server {
    public static void main(String[] args) throws IOException {
        // 线程池机制

        //思路
        //1.创建一个线程池
        //2.如果有客户链接，就创建一个线程池

        ExecutorService executorService = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(6666);

        System.out.println("服务器启动了");

        while (true) {
            // 监听,等待客户端链接 会阻塞
            final Socket socket = serverSocket.accept();
            System.out.println("链接到一个客户端");

            //创建一个线程，与之通信
            executorService.execute(() -> {
                //可以和客户端通信
                handler(socket);
            });
        }

    }

    public static void handler(Socket socket ){
        try {
            byte[] bytes = new byte[1024];
            //通过socket流获取输入流
            InputStream inputStream = socket.getInputStream();

            //循环读取客户端发送的数据
            while (true) {
                //会阻塞
                int read = inputStream.read(bytes);
                if(read != -1){
                    System.out.println(new String(bytes,0,read));
                }else{
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("关闭和client链接");
            try {

                socket.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
