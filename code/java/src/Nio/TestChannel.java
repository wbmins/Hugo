package Nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;

/**
 * @Classname TestChannel
 * @Description 通道
 * @Date 2020/3/27 下午10:54
 * @Created by pluto
 */
public class TestChannel {
    /**
     * 1.通道和目标节点的连接,负责数据的传输.本身不存储数据
     * 2.主要实现类
     * java.nio.channels.channel
     * |--FileChannel           本地
     * |--SocketChannel　      网络(tcp)
     * |--ServerSocketChannel  网络(tcp)
     * |--DatagramChannel      网络(udp)
     * 3.1获取通道getChannel()
     * 本地IO
     * 　   |--FileInputStream/FileOutputStream
     * |--RandomAcessFile
     * 网络IO
     * 　   |--Socket
     * |--ServerSocket
     * |--DatagramSocket
     * 3.2 jdk nio2
     * |--通道提供的静态方法open()
     * |--Files工具类提供的newByteChannel()
     * 4.通道之间的数据传输
     * |--transferFrom
     * |--transferTo
     * 5.分散和聚集
     * |--分散读取scatter reads 通道数据分散多个缓冲区
     * |--聚集写入gather writes　将多个缓冲区聚集到一个通道中
     * 6.字符集
     * |--字符串到字符数据
     * |--字符数据到字符串
     */
    //字符集
    @Test
    public void test6() {
        Map<String, Charset> map = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> set = map.entrySet();
        for (Map.Entry<String, Charset> entry : set) {
            System.out.println(entry.getKey() + "===" + entry.getValue());
        }
    }

    //分散读取聚集写入
    @Test
    public void test5() throws IOException {
        RandomAccessFile raf = new RandomAccessFile(
                "/home/pluto/Download/1.jpg", "rw");
        //获取通道
        FileChannel fileChannel = raf.getChannel();

        //分配指定大小缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(600);
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(1024);

        //分散读取
        ByteBuffer[] bf = {byteBuffer, byteBuffer1};
        //fileChannel.read(bf);

        //聚集写入
        RandomAccessFile randomAccessFile = new RandomAccessFile(
                "/home/pluto/Download/2.jpg", "rw");
        FileChannel fileChannel1 = randomAccessFile.getChannel();
        fileChannel1.write(bf);
    }

    //通道数据传输(直接缓冲区)
    @Test
    public void test3() throws IOException {
        FileChannel infileChannel = FileChannel.open(Paths.get(
                "/home/pluto/Download/1.jpg"), StandardOpenOption.READ);
        FileChannel outfileChannle = FileChannel.open(Paths.get(
                "/home/pluto/Download/3.jpg"),
                StandardOpenOption.WRITE, StandardOpenOption.READ,
                StandardOpenOption.CREATE);

        infileChannel.transferTo(0, infileChannel.size(), outfileChannle);//下面和这一行效果一样
        //outfileChannle.transferFrom(infileChannel,0,infileChannel.size());
        infileChannel.close();
        outfileChannle.close();
    }

    //使用直接缓冲区完成文件复制(内存映射文件)
    @Test
    public void test1() throws IOException {
        FileChannel infileChannel = FileChannel.open(Paths.get(
                "/home/pluto/Download/1.jpg"), StandardOpenOption.READ);
        FileChannel outfileChannle = FileChannel.open(Paths.get(
                "/home/pluto/Download/3.jpg"), StandardOpenOption.WRITE,
                StandardOpenOption.READ, StandardOpenOption.CREATE);

        //内存映射文件
        MappedByteBuffer inmapbufer = infileChannel.map(
                FileChannel.MapMode.READ_ONLY, 0, infileChannel.size());
        MappedByteBuffer outmapbufer = outfileChannle.map(
                FileChannel.MapMode.READ_WRITE, 0, infileChannel.size());

        //直接对缓冲区进行读写
        byte[] by = new byte[inmapbufer.limit()];
        inmapbufer.get(by);
        outmapbufer.put(by);
        infileChannel.close();
        outfileChannle.close();
    }

    //利用通道完成文件的复制(非直接缓冲区)
    @Test
    public void test() {
        FileInputStream fio = null;
        FileOutputStream foo = null;
        FileChannel channeli = null;
        FileChannel channelo = null;
        try {
            fio = new FileInputStream("/home/pluto/Download/1.jpg");
            foo = new FileOutputStream("/home/pluto/Download/2.jpg");
            //1.获取通道
            channeli = fio.getChannel();
            channelo = foo.getChannel();
            //2.分配指定大小缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            //3.通道数据存入缓冲区
            while (channeli.read(byteBuffer) != 1) {
                byteBuffer.flip(); //切换读模式
                channelo.write(byteBuffer);//缓冲区数据写入通道
                byteBuffer.clear();//清空缓冲区
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channelo != null) {
                try {
                    channelo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (channeli != null) {
                try {
                    channeli.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (foo != null) {
                try {
                    foo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fio != null) {
                try {
                    fio.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
