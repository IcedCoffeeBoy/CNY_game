package com.dsta.CNYBackend.Game;

import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

public class SocketBroker extends AbstractSecurityWebSocketMessageBrokerConfigurer {
    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
