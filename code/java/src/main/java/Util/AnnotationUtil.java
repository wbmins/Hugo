package main.java.Util;

import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.annotation.Annotation;

import java.io.IOException;

class Student{

    public void execute() {
    }
}

public class AnnotationUtil {

    //https://blog.csdn.net/qq_32506963/article/details/72851713
    //动态添加注解
    public static void main(String[] args) throws NotFoundException, CannotCompileException {
        replaceMethodBody("Student", "execute", "System.out.println(\"this method is changed dynamically!\");");
        Student student = new Student();
        student.execute();
    }

    public static void replaceMethodBody(String clazzName, String methodName, String newMethodBody) {
        try {
            CtClass clazz = ClassPool.getDefault().get(clazzName);
            CtMethod method = clazz.getDeclaredMethod(methodName);
            method.setBody(newMethodBody);
            clazz.toClass();
        } catch (NotFoundException | CannotCompileException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeClass(String className) throws NotFoundException, CannotCompileException, IOException {
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(new ClassClassPath(this.getClass()));
        CtClass clazz = pool.get(className);
        ClassFile ccFile = clazz.getClassFile();
        ConstPool constpool = ccFile.getConstPool();

        //get annotation
        CtClass executor = pool.get("com.javassist.test.Executor");

        String fieldName = "hello";
        // 增加字段
        CtField field = new CtField(executor,fieldName,clazz);
        field.setModifiers(Modifier.PUBLIC);
        FieldInfo fieldInfo = field.getFieldInfo();

        // 属性附上注解
//        AnnotationsAttribute fieldAttr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
//        Annotation autowired = new Annotation("org.springframework.beans.factory.annotation.Autowired",constpool);
//        fieldAttr.addAnnotation(autowired);
//        fieldInfo.addAttribute(fieldAttr);
        clazz.addField(field);

        clazz.writeFile();
    }

}
