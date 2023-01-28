package org.luvsa.html;

import jdk.jfr.Name;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.luvsa.io.Files;

import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * xml 或 html 标签
 *
 * @author Aglet
 * @create 2023/1/11 9:36
 */
@Data
@NoArgsConstructor
public class Label {
    /**
     * 参见的标签
     */
    private static final Map<String, Label> LABELS = new ConcurrentHashMap<>(32);

    static {
        var load = ServiceLoader.load(Resource.class);
        for (var resource : load) {
            var aClass = resource.getClass();
            var name = aClass.getAnnotation(Name.class);
            if (name == null) {
                throw new UnsupportedOperationException();
            }
            Files.readResource(name.value(), (text, throwable) -> {
                if (throwable == null) {
                    var split = text.split(";");
                    for (var s : split) {
                        resource.accept(s);
                    }
                } else {
                    throw new RuntimeException(throwable);
                }
            });
        }
    }

    /**
     * 标签名称
     */
    private String name;

    /**
     * 始终使用此标记的小写版本，无论大小写保留模式如何
     */
    private String alias;

    /**
     * 块级标签
     */
    private boolean block = true;

    /**
     * 应格式化为块
     */
    private boolean formatted = true;

    private boolean empty = false;

    /**
     * 自己结束， 例如: {@code  <foo />; <br >}
     * <p>
     * 可以自行关闭。 用于自动关闭的未知标记，而不强制它们为空。
     */
    private boolean autism = false;

    /**
     * 用于前置、文本区域、脚本等
     */
    private boolean scope = false;

    public Label(String name) {
        this.name = name.trim();
        this.alias = name.toLowerCase();
    }

    static void register(Label label) {
        LABELS.put(label.name, label);
    }

    private static Label of(String s) {
        return new Label(s);
    }

    public static Label get(String s) {
        return LABELS.computeIfAbsent(s, Label::new);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var label = (Label) o;
        return block == label.block && formatted == label.formatted && empty == label.empty && autism == label.autism &&
                scope == label.scope && Objects.equals(name, label.name) && Objects.equals(alias, label.alias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, alias, block, formatted, empty, autism, scope);
    }

    @Override
    public String toString() {
        return name;
    }




}
