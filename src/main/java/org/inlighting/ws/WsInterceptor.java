package org.inlighting.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.security.Principal;
import java.util.Map;

public class WsInterceptor implements HandshakeInterceptor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        ServletServerHttpRequest req = (ServletServerHttpRequest) request;
        String token = req.getServletRequest().getParameter("token");
//        HttpSession session = req.getServletRequest().getSession(false);
        Principal user = parseToken(token);
        if (user == null) {   //如果token认证失败user为null，返回false拒绝握手
            return false;
        }
        attributes.put("user", user);
        attributes.put("WEBSOCKET_USERNAME", "test_"+ user.getName());
        logger.info("beforeHandshake:" + user.getName());
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2, Exception arg3) {
        logger.info("afterHandshake");
    }

    /**
     * 根据token认证授权
     *
     * @param token
     */
    private Principal parseToken(String token) {
        logger.info("token:"+ token);
        if (token == null || token.trim().length() == 0) {
            return null;
        }
        return new LocalPrincipal(token);
    }
}