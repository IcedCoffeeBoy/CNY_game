package com.dsta.CNYBackend.game;

import com.dsta.CNYBackend.user.UsersService;
import com.dsta.CNYBackend.user.model.UserScore;
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
import java.util.List;
import java.util.Map;

/**
 * Controller for REST api endpoint
 */
@RestController
@RequestMapping(value = "/api/game")
public class GameController {
    private GameService gameService;
    private UsersService userService;

    @Autowired
    public GameController(GameService gameService, UsersService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

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

    @ApiOperation(value = "Move to the next game state")
    @GetMapping(value = "/next", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> nextQuestionState() {
        if (!this.gameService.checkExistingGame()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "There is not existing game");
            return ResponseEntity.badRequest().body(map);
        }
        this.gameService.nextQuestionState();
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

    @ApiOperation(value = "Delete all answer and score and reset the game to EMPTY")
    @GetMapping(value = "/reset", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> resetGame(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "Not user login");
            return ResponseEntity.badRequest().body(map);
        }

        if (!"admin".equals(authentication.getName())) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "Only admin can reset the game");
            return ResponseEntity.badRequest().body(map);
        }

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

    @ApiOperation(value = "Get all users score in ranked order. If size is not given, the default is 10  ")
    @GetMapping("/rank")
    public ResponseEntity<List<UserScore>> getUsersScore(@RequestParam(value = "size", required = false) Integer size) {
        if (size == null) {
            size = 10;
        }
        return ResponseEntity.ok(this.userService.getUserScores(size));
    }

    @ApiOperation(value = "Get all unanswered users")
    @GetMapping("/waiting")
    public ResponseEntity<List<String>> getWaiting() {
        return ResponseEntity.ok(this.gameService.getWaitingParticipants());
    }


}
