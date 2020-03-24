package proxy;

import org.junit.Test;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pluto
 * @date 2020/3/19 下午4:39
 */
public class ProxyTest {

    @Test
    public void test1() {
        final List<String> list = new ArrayList<>();
        @SuppressWarnings("unchecked")
        List<String> proxyInstance = (List<String>) Proxy.newProxyInstance(
                list.getClass().getClassLoader(),
                list.getClass().getInterfaces(),
                (proxy, method, args) -> method.invoke(list, args)
        ); //lambda表达式
        proxyInstance.add("mins1");
        proxyInstance.add("mins2");
        proxyInstance.add("mins3");
        proxyInstance.add("mins4");
        System.out.println(list);
    }
}
