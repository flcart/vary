package org.luvsa.web;

import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @author Aglet
 * @create 2023/3/21 14:23
 */
public interface Interceptor {
    /**
     * 添加拦截器
     *
     * @param request 请求
     */
    void accept(ServerHttpRequest request);
}
