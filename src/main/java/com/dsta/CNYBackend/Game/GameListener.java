package com.dsta.CNYBackend.Game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletResponse;

@Controller
public class GameListener {
    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private GameComponent gameComponent;

    @MessageMapping("/game")
    @SendTo("/topic/game")
    public GameState enter(HttpServletResponse response) throws Exception {
        return this.gameComponent.getGameState();
    }

    public void sendGameState(GameState gameState) {
        this.template.convertAndSend("/topic/game", gameState);
    }


}
