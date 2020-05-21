package org.inlighting.config;

import org.inlighting.ws.WsHandler;
import org.inlighting.ws.WsInterceptor;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

//@Configuration
//@EnableWebSocket
public class WsConfig implements WebSocketConfigurer {
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WsHandler(), "/bitgame")
            .addInterceptors(new WsInterceptor())
            .setAllowedOrigins("*");
    }
}