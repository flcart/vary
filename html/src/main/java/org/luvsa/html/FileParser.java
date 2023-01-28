package org.luvsa.html;

import org.luvsa.exception.ValueException;
import org.luvsa.html.Token.Reader;
import org.luvsa.io.Resources;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.concurrent.SubmissionPublisher;

/**
 * @author Aglet
 * @create 2023/1/16 8:57
 */
public class FileParser implements Parser {
    private final Stack<Node> stack = new Stack<>();
    private final String resource;
    public FileParser(String resource) {
        this.resource = resource;
    }

    /**
     * 解析 resolve
     *
     * @return 将代码解析成 node
     */
    public Node resolve() {
        try (var stream = Resources.asStream(resource); var streamReader = new InputStreamReader(stream);
             var bufferedReader = new BufferedReader(streamReader)) {
            var reader = new Reader(bufferedReader);
            var tokenizer = new Tokenizer(reader, new SubmissionPublisher<>());
            while (true) {
                var item = tokenizer.next();
                if (item == null) {
                    break;
                }
                onNext(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stack.pop();
    }

    private void onNext(Node item) {
        if (stack.isEmpty()) {
            stack.push(item);
        } else if (item.isFinished()) {
            while (true) {
                var pop = stack.pop();
                if (stack.isEmpty()) {
                    if (pop.match(item)) {
                        throw new ValueException(pop);
                    }
                    throw new IllegalStateException("Node prefix[" + pop + "] not match suffix[" + item + "]");
                }
                var peek = stack.peek();
                if (pop.match(item)) { //  匹配到结束标签
                    if (peek.add(pop)) {
                        break;
                    }
                    throw new IllegalStateException("");
                }
                // 未匹配到结束标签
                if (peek.add(pop)) {
                    continue;
                }
                throw new IllegalStateException();
            }
        } else if (item instanceof Text || item.isEmpty()) {
            stack.peek().add(item);
        } else {
            stack.push(item);
        }
    }
}
