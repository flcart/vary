package org.luvsa.html;

import org.junit.jupiter.api.Test;
import org.luvsa.html.Token.Reader;
import org.luvsa.io.Resources;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.concurrent.SubmissionPublisher;

/**
 * @author Aglet
 * @create 2023/1/11 16:23
 */
class TokenTest {

    @Test
    void reset() {
    }

    @Test
    void indexOf() {
        var nodes = new Stack<Node>();
        try (var stream = Resources.asStream("index.html"); var streamReader = new InputStreamReader(stream); var bufferedReader = new BufferedReader(streamReader)) {
            var reader = new Reader(bufferedReader);
            var tokenizer = new Tokenizer(reader, new SubmissionPublisher<>());

            while (true) {
                var read = tokenizer.read();
                if (read == null) {
                    break;
                }
                if (nodes.isEmpty()) {
                    nodes.push(read);
                } else {
                    System.out.println(read);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}