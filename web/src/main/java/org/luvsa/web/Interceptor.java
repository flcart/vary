package org.luvsa.web;

import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @author Aglet
 * @create 2023/3/21 14:23
 */
public interface Interceptor {
    void accept(ServerHttpRequest request);
}
