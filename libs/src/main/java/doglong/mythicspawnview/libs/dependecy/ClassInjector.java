package doglong.mythicspawnview.libs.dependecy;

import doglong.mythicspawnview.libs.Main;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

//注入器
public class ClassInjector {
    private static final MethodHandle addUrlHandle;
    private static final Object ucp;

    static {
        //通过反射获取ClassLoader addUrl 方法，因为涉及java17 无奈使用UnSafe方法
        try {
            Class<?> clazz = Class.forName("sun.misc.Unsafe");
            Field f = clazz.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            Object unsafe = f.get(null);
            Method xGetObject = clazz.getMethod("getObject", Object.class, long.class);
            Method xObjectFieldOffset = clazz.getMethod("objectFieldOffset", Field.class);
            Field ucpField = URLClassLoader.class.getDeclaredField("ucp");
            long offset = (long) xObjectFieldOffset.invoke(unsafe, ucpField);
            ucp = xGetObject.invoke(unsafe, Main.class.getClassLoader(), offset);
            Field lookupField = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
            Method xStaticFieldOffset = clazz.getMethod("staticFieldOffset", Field.class);
            Method xStaticFieldBase = clazz.getMethod("staticFieldBase", Field.class);
            offset = (long) xStaticFieldOffset.invoke(unsafe, lookupField);
            MethodHandles.Lookup lookup = (MethodHandles.Lookup) xGetObject.invoke(unsafe, xStaticFieldBase.invoke(unsafe, lookupField), offset);
            addUrlHandle = lookup.findVirtual(ucp.getClass(), "addURL", MethodType.methodType(void.class, URL.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将URl添加进插件的ClassLoader
     */
    public static void addURL(URL url) {

        try {
            addUrlHandle.invoke(ucp, url);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

    }
}