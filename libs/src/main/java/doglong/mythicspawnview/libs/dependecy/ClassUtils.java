package doglong.mythicspawnview.libs.dependecy;


import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FilenameFilter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ClassUtils {


    public static List<Class<?>> forFolder(ClassLoader classLoader, File folder, Function<Class<?>, Boolean> filter) {
        List<Class<?>> classList = new ArrayList<>();
        try {
            if (!folder.exists()) {
                return classList;
            }
            FilenameFilter fileNameFilter = (dir, name) -> name.endsWith(".jar");
            File[] jars = folder.listFiles(fileNameFilter);
            if (jars == null) {
                return classList;
            }
            for (File file : jars) {
                URL jar = file.toURI().toURL();
                try(URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{jar}, classLoader)) {
                    JarInputStream jarInputStream = new JarInputStream(jar.openStream());
                    while (true) {
                        JarEntry nextJarEntry = jarInputStream.getNextJarEntry();
                        if (nextJarEntry == null) {
                            break;
                        }
                        String name = getName(nextJarEntry);
                        if (name == null) {
                            continue;
                        }
                        String cname = name.substring(0, name.lastIndexOf(".class"));
                        Class<?> loadClass = urlClassLoader.loadClass(cname);
                        if (filter == null || filter.apply(loadClass)) {
                            classList.add(loadClass);
                        }
                    }
                }
            }
            return classList;
        } catch (Throwable ignored) {
        }
        return classList;
    }

    public static List<Class<?>> forName(Plugin plugin, String forName, Function<Class<?>, Boolean> filter) throws UnsupportedEncodingException {
        ClassLoader classLoader = plugin.getClass().getClassLoader();
        File file = new File(URLDecoder.decode(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().getPath(), StandardCharsets.UTF_8.toString()));
        return forName(file, classLoader, forName, filter);
    }

    public static List<Class<?>> forName(File file, ClassLoader classLoader, String forName, Function<Class<?>, Boolean> filter) {
        List<Class<?>> classList = new ArrayList<>();
        try {
            URL jar = file.toURI().toURL();
            try(URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{jar}, classLoader)) {
                JarInputStream jarInputStream = new JarInputStream(jar.openStream());
                while (true) {
                    JarEntry nextJarEntry = jarInputStream.getNextJarEntry();
                    if (nextJarEntry == null) {
                        break;
                    }
                    String name = getName(nextJarEntry);
                    if (name == null) {
                        continue;
                    }
                    if (name.startsWith(forName)) {
                        String cname = name.substring(0, name.lastIndexOf(".class"));
                        try {
                            Class<?> loadClass = urlClassLoader.loadClass(cname);
                            if (filter == null || filter.apply(loadClass)) {
                                classList.add(loadClass);
                            }
                        }catch (Throwable ignored) {
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        }
        return classList;
    }

    private static String getName(JarEntry jarEntry) {
        String name = jarEntry.getName();
        if (name.isEmpty()) {
            return null;
        }
        if (!name.endsWith(".class")) {
            return null;
        }
        return name.replace("/", ".");
    }
}
