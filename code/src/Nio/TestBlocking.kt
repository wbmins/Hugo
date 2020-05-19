package Nio

import org.junit.Test
import java.io.IOException
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

/**
 * @Classname TestBlocking
 * @Description blocking io
 * @Date 2020/3/28 上午12:21
 * @Created by pluto
 */
class TestBlocking {
    /**
     * 使用nio核心
     * - 通道连接channel
     * java.nio.channels.channel
     * * |--FileChannel           本地
     * * |--SocketChannel　      网络(tcp)
     * * |--ServerSocketChannel  网络(tcp)
     * * |--DatagramChannel      网络(udp)
     * * |--Pipe.SinkChannel
     * * |--Pipe.SourceChannel
     * - 缓冲区存数据buffer
     * - 选择器selecttablechannel的多路复用器,用于监控复用器io状况
     */
    @Test
    @Throws(IOException::class)
    fun client() {
        //客户端
        //获取通道
        val socketChannel = SocketChannel.open(
                InetSocketAddress("127.0.0.1", 9999))
        val inf = FileChannel.open(Paths.get(
                "/home/pluto/Download/1.jpg"), StandardOpenOption.READ)
        //分配指定大小缓冲区
        val byteBuffers = ByteBuffer.allocate(1024 * 1024 * 4)

        //读取文件发送到服务端
        while (inf.read(byteBuffers) != -1) {
            byteBuffers.flip()
            socketChannel.write(byteBuffers)
            byteBuffers.clear()
        }
        //关闭通道
        socketChannel.close()
    }

    @Test
    @Throws(IOException::class)
    fun server() {
        //服务端
        //获取通道
        val serverSocketChannel = ServerSocketChannel.open()
        val outf = FileChannel.open(Paths.get(
                "/home/pluto/Download/2.jpg"),
                StandardOpenOption.WRITE, StandardOpenOption.CREATE)
        //绑定端口
        serverSocketChannel.bind(InetSocketAddress(9999))
        //获取客户端连接通道
        val socketChannel = serverSocketChannel.accept()
        //分配指定大小缓冲区
        val buf = ByteBuffer.allocate(1024 * 1024 * 4)
        //读取客户端数据
        while (socketChannel.read(buf) != -1) {
            buf.flip()
            outf.write(buf)
            buf.clear()
        }
        //关闭
        socketChannel.close()
        buf.clear()
        serverSocketChannel.close()
    }

    @Test
    @Throws(IOException::class)
    fun client1() {
        //客户端
        //获取通道
        val socketChannel = SocketChannel.open(
                InetSocketAddress("127.0.0.1", 8888))
        val inf = FileChannel.open(Paths.get(
                "/home/pluto/Download/1.jpg"), StandardOpenOption.READ)
        //分配指定大小缓冲区
        val byteBuffers = ByteBuffer.allocate(1024)

        //读取文件发送到服务端
        while (inf.read(byteBuffers) != -1) {
            byteBuffers.flip()
            socketChannel.write(byteBuffers)
            byteBuffers.clear()
        }
        //告诉服务端我发挖了
        socketChannel.shutdownOutput()
        //接收服务器端反馈
        var len = 0
        while (socketChannel.read(byteBuffers).also { len = it } != -1) {
            byteBuffers.flip()
            println(String(byteBuffers.array(), 0, len))
            byteBuffers.clear()
        }
        //关闭通道
        inf.close()
        socketChannel.close()
    }

    @Test
    @Throws(IOException::class)
    fun server1() {
        //服务端
        //获取通道
        val serverSocketChannel = ServerSocketChannel.open()
        val outf = FileChannel.open(Paths.get(
                "/home/pluto/Download/2.jpg"),
                StandardOpenOption.WRITE, StandardOpenOption.CREATE)
        //绑定端口
        serverSocketChannel.bind(InetSocketAddress(8888))
        //获取客户端连接通道
        val socketChannel = serverSocketChannel.accept()
        //分配指定大小缓冲区
        val buf = ByteBuffer.allocate(1024)
        //读取客户端数据
        while (socketChannel.read(buf) != -1) {
            buf.flip()
            outf.write(buf)
            buf.clear()
        }
        //发送反馈
        buf.put("服务端就收数据成功".toByteArray())
        buf.flip()
        socketChannel.write(buf)
        //关闭
        socketChannel.close()
        outf.close()
        serverSocketChannel.close()
    }
}
