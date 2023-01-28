package org.luvsa.html;

import org.luvsa.lang.Arrays;
import org.luvsa.lang.ContextHolder;
import org.luvsa.lang.Strings;
import org.luvsa.vary.Vary;

import java.util.*;

/**
 * @author Aglet
 * @create 2023/1/11 13:45
 */
public class Document extends Node {
    /**
     * doctype 的属性
     */
    private final List<String> attr;

    private final Map<String, Holder> holders = new HashMap<>();

    /**
     * 子 html node
     */
    private Node html;

    public Document(Collection<String> values, String... array) {
        this.attr = Arrays.merge(values, Strings::isNotEmpty, array);
    }

    @Override
    protected boolean hasAttributes() {
        return false;
    }

    @Override
    public boolean add(Node item) {
        super.add(item);
        this.html = item;
        return true;
    }

    @Override
    public String getName() {
        return "Doc";
    }

    @Override
    public String toString() {
        var text = "<!doctype " + String.join(" ", attr) + '>';
        if (html == null) {
            return text;
        }
        var conf = ContextHolder.get(Formatter.class);
        if (conf == null) {
            return text + html;
        }
        return conf.build(text, html);
    }

    @Override
    public void visit(Visitor visitor, int depth) {
        html.visit(visitor, depth);
    }

    public Node getElementById(String id) {
        var target = holders.computeIfAbsent("IDENTITY", k -> new Identity(this)).get(id);
        return Vary.change(target, Node.class);
    }

    public List<Node> getElementsByClass(String clazz) {
        var target = holders.computeIfAbsent("CLASS", k -> new Clazz(this)).get(clazz);
        return Vary.asList(target, Node.class);
    }

    public List<Node> getElementsByTag(String tagName) {
        var target = holders.computeIfAbsent("TAG", k -> new Tag(this)).get(tagName);
        return Vary.asList(target, Node.class);
    }

    public interface Holder {

        Object get(String id);

    }
}
