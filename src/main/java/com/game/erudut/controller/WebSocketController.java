package com.game.erudut.controller;

import com.game.erudut.service.GameService;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @Autowired
    private GameService gameService;

    @MessageMapping("/answer")
    @SendTo("/topic/score")
    public ResponseEntity<?> processAnswer(Message message) {
        return gameService.processAnswer(message);
    }
}
//Это контроллер
