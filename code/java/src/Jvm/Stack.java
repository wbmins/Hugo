package Jvm;

public class Stack {
    public static void main(String[] args) {
        int j = 2;
        int i = 3;
        int m = i + j;
    }
    /** 反编译得到的字节码 javap -v xxx.class
     * Classfile /home/mins/Github/blog/code/out/production/code/Jvm/Stack.class
     *   Last modified 2020年5月15日; size 432 bytes
     *   MD5 checksum 01797e48b20acdebb384fc321527f3c2
     *   Compiled from "Stack.java"
     * public class Jvm.Stack
     *   minor version: 0
     *   major version: 55
     *   flags: (0x0021) ACC_PUBLIC, ACC_SUPER
     *   this_class: #2                          // Jvm/Stack
     *   super_class: #3                         // java/lang/Object
     *   interfaces: 0, fields: 0, methods: 2, attributes: 1
     * Constant pool:
     *    #1 = Methodref          #3.#21         // java/lang/Object."<init>":()V
     *    #2 = Class              #22            // Jvm/Stack
     *    #3 = Class              #23            // java/lang/Object
     *    #4 = Utf8               <init>
     *    #5 = Utf8               ()V
     *    #6 = Utf8               Code
     *    #7 = Utf8               LineNumberTable
     *    #8 = Utf8               LocalVariableTable
     *    #9 = Utf8               this
     *   #10 = Utf8               LJvm/Stack;
     *   #11 = Utf8               main
     *   #12 = Utf8               ([Ljava/lang/String;)V
     *   #13 = Utf8               args
     *   #14 = Utf8               [Ljava/lang/String;
     *   #15 = Utf8               j
     *   #16 = Utf8               I
     *   #17 = Utf8               i
     *   #18 = Utf8               m
     *   #19 = Utf8               SourceFile
     *   #20 = Utf8               Stack.java
     *   #21 = NameAndType        #4:#5          // "<init>":()V
     *   #22 = Utf8               Jvm/Stack
     *   #23 = Utf8               java/lang/Object
     * {
     *   public Jvm.Stack();
     *     descriptor: ()V
     *     flags: (0x0001) ACC_PUBLIC
     *     Code:
     *       stack=1, locals=1, args_size=1
     *          0: aload_0
     *          1: invokespecial #1                  // Method java/lang/Object."<init>":()V
     *          4: return
     *       LineNumberTable:
     *         line 3: 0
     *       LocalVariableTable:
     *         Start  Length  Slot  Name   Signature
     *             0       5     0  this   LJvm/Stack;
     *
     *   public static void main(java.lang.String[]);
     *     descriptor: ([Ljava/lang/String;)V
     *     flags: (0x0009) ACC_PUBLIC, ACC_STATIC
     *     Code:
     *       stack=2, locals=4, args_size=1
     *          0: iconst_2
     *          1: istore_1
     *          2: iconst_3
     *          3: istore_2
     *          4: iload_2
     *          5: iload_1
     *          6: iadd
     *          7: istore_3
     *          8: return
     *       LineNumberTable:
     *         line 5: 0
     *         line 6: 2
     *         line 7: 4
     *         line 8: 8
     *       LocalVariableTable:
     *         Start  Length  Slot  Name   Signature
     *             0       9     0  args   [Ljava/lang/String;
     *             2       7     1     j   I
     *             4       5     2     i   I
     *             8       1     3     m   I
     * }
     * SourceFile: "Stack.java"
     */
}
