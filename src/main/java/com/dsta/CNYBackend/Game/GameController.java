package com.dsta.CNYBackend.game;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @GetMapping(value = "/start", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> startGame(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
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

    @GetMapping(value = "/next", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping(value = "/end", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping(value = "/reset", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> resetGame() {
        if (!this.gameService.checkExistingGame()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "There is not existing game");
            return ResponseEntity.badRequest().body(map);
        }
        this.gameService.resetGame();
        Map<String, String> map = new HashMap<String, String>();
        map.put("Success", "Reset game");
        return ResponseEntity.ok(map);
    }

    @GetMapping(value = "/open", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> openGame() {
        if (this.gameService.checkExistingGame()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "There is an existing game");
            return ResponseEntity.badRequest().body(map);
        }
        this.gameService.openGame();
        Map<String, String> map = new HashMap<String, String>();
        map.put("Success", "Open game");
        return ResponseEntity.ok(map);
    }


    @GetMapping("/setTimer")
    public Integer setTimer(@RequestParam(value = "timer", required = true) int timer) {
        this.gameService.setGameTimer(timer);
        return timer;
    }

    @GetMapping(value = "/state", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameState> getState() {
        return ResponseEntity.ok(this.gameService.getGameState());
    }

}
