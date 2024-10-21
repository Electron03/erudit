package com.game.erudut.controller;

import com.game.erudut.repository.PlayerRepository;
import com.game.erudut.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.BinaryMessage;

import java.io.IOException;

public class MyWebSocketHandler extends TextWebSocketHandler {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Client connected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String receivedMessage = message.getPayload();
        System.out.println("Received message: " + receivedMessage);

        String responseMessage = roomRepository.findAll().stream()
                .flatMap(room -> room.getPlayers().stream())
                .toList().toString();

        sendMessage(session, responseMessage);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // Обработка ошибок соединения
        System.out.println("Error in WebSocket connection: " + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Действия после закрытия соединения
        System.out.println("Connection closed: " + session.getId() + ", status: " + status);
    }

    private void sendMessage(WebSocketSession session, String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            System.out.println("Error sending message: " + e.getMessage());
        }
    }
}
