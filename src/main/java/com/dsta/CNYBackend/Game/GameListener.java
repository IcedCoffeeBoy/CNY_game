package com.dsta.CNYBackend.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

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
