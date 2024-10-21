package com.game.erudut.config;

import com.game.erudut.controller.MyWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

    @Configuration
    @EnableWebSocket
    public class WebSocketConfig implements WebSocketConfigurer {
        @Override
        public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
            registry.addHandler(new MyWebSocketHandler(), "/ws").setAllowedOrigins("*");
        }
    }



