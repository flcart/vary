package org.luvsa.io;

import org.luvsa.lang.Strings;

import java.io.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Aglet
 * @create 2022/10/12 15:36
 */
public class Files {

    /**
     * 获取文件拓展后缀名
     *
     * @param filename 文件名
     * @return 拓展名
     */
    public static String extension(String filename) {
        if (filename == null) {
            return "";
        }
        var index = filename.lastIndexOf(".");
        if (index < 0) {
            return "";
        }
        return filename.substring(index + 1);
    }

    /**
     * 读取资源目录下的指定文件
     *
     * @param name     指定文件名称
     * @param consumer 文件内容处理器
     */
    public static void readResource(String name, BiConsumer<String, Throwable> consumer) {
        if (Strings.isEmpty(name)) {
            throw new IllegalArgumentException("Invalid resource name : " + name);
        }
        var stream = Resources.asStream(name);
        read(stream, consumer);
    }

    /**
     * 遍历文件流
     *
     * @param stream   文件流
     * @param consumer 文件内容处理器
     */
    private static void read(InputStream stream, BiConsumer<String, Throwable> consumer) {
        try {
            read(stream, s -> consumer.accept(s, null));
        } catch (Exception e) {
            consumer.accept(null, e);
        }
    }

    /**
     * 遍历文件流
     *
     * @param stream   文件流
     * @param consumer 文件内容处理器
     */
    public static void read(InputStream stream, Consumer<String> consumer) throws Exception {
        if (stream == null) {
            return;
        }
        try (var input = new InputStreamReader(stream); var reader = new BufferedReader(input)) {
            read(reader, consumer);
        }
    }

    /**
     * 读取文件内容
     *
     * @param reader   BufferedReader
     * @param consumer 消费者
     * @throws IOException 读取异常
     */
    public static void read(BufferedReader reader, Consumer<String> consumer) throws Exception {
        read0(reader::readLine, consumer);
    }

    private static void read0(Reader reader, Consumer<String> consumer) throws Exception {
        if (reader == null) {
            return;
        }
        do {
            var s = reader.readLine();
            if (s == null) {
                break;
            }
            consumer.accept(s);
        } while (true);
    }

    private interface Reader {
        String readLine() throws IOException;
    }
}
