### nio（non-blocking io） 学习

#### Selector Channel 和 Buffer 的关系

1. 每个 channel 都会对应一个 buffer

2. selector 对应一个线程，一个线程对应多个 channel 连接

3. channel 是注册到selector

4. 程序/线程切换那个 channel 是由 event决定的

5. selector 会根据不同的事件，在各个channel 切换

6. buffer 就是一个内存块，底层是一个数组

7. 数据的读写是通过 buffer ，bio 要么是输入流或者是输出流，nio 的buffer 是可以读写切换的（filp）

8. channel 是双向的，可以返回底层操作系统的情况，例如 linux

#### Buffer

> 真正的数据存在数组

1. capacity 容量

2. limit 缓冲区当前的终点

3. position 下一个要被读/写元素的位置

4. mark 标记