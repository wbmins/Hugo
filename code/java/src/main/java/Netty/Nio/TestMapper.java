package Netty.Nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * mappedByteBuffer 可以让文件在堆外内存修改，操作系统不需要拷贝一次
 */
public class TestMapper {

    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("test.properties","rw");

        FileChannel channel = randomAccessFile.getChannel();

        /**
         * 参数一：读写模式
         * 参数二：0可以修改开始位置
         * 参数三，可以修改结束位置
         */
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        map.put(0,(byte)'h');
        map.put(3,(byte)6);

        randomAccessFile.close();

    }

}
