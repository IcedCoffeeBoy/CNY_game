package com.dsta.CNYBackend.Game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GameListener {
    @Autowired
    private SimpMessagingTemplate template;


    @Autowired
    private GameComponent gameComponent;

    @MessageMapping("/game")
    @SendTo("/topic/game")
    public void enter() throws Exception {
        this.template.convertAndSend("/topic/game", this.gameComponent.getGameState());
    }

    public void sendGameState(GameState gameState) {
        this.template.convertAndSend("/topic/game", gameState);
    }


}
