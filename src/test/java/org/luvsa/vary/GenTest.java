package org.luvsa.vary;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.luvsa.vary.bool.BProvider;
import org.luvsa.vary.chrono.CProvider;
import org.luvsa.vary.date.DProvider;
import org.luvsa.vary.instant.Provider;
import org.luvsa.vary.number.NProvider;
import org.luvsa.vary.other.OProvider;
import org.luvsa.vary.string.SProvider;
import org.luvsa.vary.string.array.AProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *
 * @author Aglet
 * @create 2022/7/1 17:22
 */
class GenTest {

    @ParameterizedTest
    @ValueSource(classes = {Factory.class, BProvider.class, CProvider.class, DProvider.class, Provider.class,
            org.luvsa.vary.local.Provider.class, NProvider.class, OProvider.class, SProvider.class, AProvider.class, org.luvsa.vary.zone.Provider.class})
    void gen(Class<?> service) throws IOException {

        var aClass = Vary.class;
        var domain = aClass.getProtectionDomain();
        var source = domain.getCodeSource();
        var location = source.getLocation();
        var file = new File(location.getPath());
        var root = file.getPath();
        var path = Path.of(root);
        var from = path.getNameCount();

        var servicesPath = Path.of( "src/main/resources/META-INF/services/" + service.getName());
        var modulePath = Path.of( "src/main/resources/META-INF/versions.9/module-info.java");

        var builder = new StringBuilder();
        Files.walkFileTree(path, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                var to = file.getNameCount();
                var sub = file.subpath(from, to);
                var s = sub.toString();
                var name = s.replace("\\", ".");
                if (name.endsWith("-info.class")) {
                    return FileVisitResult.CONTINUE;
                }
                try {
                    var index = name.lastIndexOf(".");
                    var className = name.substring(0, index);
                    var clazz = Class.forName(className);
                    if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
                        return FileVisitResult.CONTINUE;
                    }
                    if (service.isAssignableFrom(clazz)) {
                        builder.append(clazz.getName()).append('\n');
                    }
                } catch (ClassNotFoundException e) {
                    System.err.println(name);
                }
                return FileVisitResult.CONTINUE;
            }
        });
        write(servicesPath, builder);
    }

    public static void write(Path path, CharSequence data) throws IOException {
        write(path, data, false);
    }

    public static void write(Path path, CharSequence data, boolean append) throws IOException {
        File file = path.toFile();
        OpenOption option = append ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING;
        if (file.exists()) {
            Files.writeString(path, data, option);
        } else {
            File parent = file.getParentFile();
            if (parent.exists() && file.createNewFile()) {
                Files.writeString(path, data, option);
            } else if (parent.mkdirs() && file.createNewFile()) {
                Files.writeString(path, data, option);
            } else {
                throw new IOException("create file failure, path : " + path);
            }
        }
    }
}
