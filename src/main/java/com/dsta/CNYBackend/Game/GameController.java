package com.dsta.CNYBackend.Game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/game")
public class GameController {
    @Autowired
    private GameService gameService;

    @GetMapping("/start")
    public ResponseEntity<Map<String, String>> startGame(Authentication authentication) {
        if (authentication != null  && authentication.isAuthenticated()) {
            System.out.println(authentication.getName());
        }

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

    @GetMapping("/next")
    public ResponseEntity<Map<String, String>> nextQuestion() {
        if (!this.gameService.checkExistingGame()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "There is not existing game");
            return ResponseEntity.badRequest().body(map);
        }
        this.gameService.nextQuestion();
        Map<String, String> map = new HashMap<String, String>();
        map.put("Success", "Next question");
        return ResponseEntity.ok(map);
    }

    @GetMapping("/end")
    public ResponseEntity<Map<String, String>> endGame() {
        if (!this.gameService.checkExistingGame()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "There is not existing game");
            return ResponseEntity.badRequest().body(map);
        }
        this.gameService.endGame();
        Map<String, String> map = new HashMap<String, String>();
        map.put("Success", "End game");
        return ResponseEntity.ok(map);
    }

    @GetMapping("/setTimer")
    public Integer setTimer(@RequestParam(value = "timer", required = true) int timer) {
        this.gameService.setGameTimer(timer);
        return timer;
    }

    @GetMapping("/state")
    public GameState getState() {
        return this.gameService.getGameState();
    }

}
