package me.kreal.avalon.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // Set prefix for the endpoint that the client listens for our messages from
        registry.enableSimpleBroker("/");

        // Set prefix for endpoints the client will send messages to
        registry.setApplicationDestinationPrefixes("/message");

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        // Registers the endpoint where the connection will take place
        registry.addEndpoint("/stomp")
                .setAllowedOrigins("http://localhost:4200/", "http://localhost:4200", "http://www.playlobby.club/", "http://www.playlobby.club", "http://playlobby.club/", "http://playlobby.club")
//                .setAllowedOriginPatterns("*")
                .withSockJS();

    }

}


