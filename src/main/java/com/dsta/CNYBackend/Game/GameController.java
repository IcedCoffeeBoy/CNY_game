package com.dsta.CNYBackend.Game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/game")
public class GameController {
    @Autowired
    private GameService gameService;

    @GetMapping("/start")
    public ResponseEntity<Map<String, String>> startGame() {
        if (this.gameService.checkExistingGame()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "There is an existing game");
            return ResponseEntity.badRequest().body(map);
        }
        this.gameService.startGame();
        Map<String, String> map = new HashMap<String, String>();
        map.put("Success", "The game has started");
        return ResponseEntity.ok(map);
    }

}