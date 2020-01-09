package com.dsta.CNYBackend.poll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/poll")
public class PollController {
    private PollService pollService;

    @Autowired
    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @GetMapping(value = "/{position}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPoll(@PathVariable("position") Integer position) {
        Optional<Poll> exist = this.pollService.getPollByPosition(position);
        if (exist.isPresent()) {
            return ResponseEntity.ok(exist.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No record are found");
        }
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Poll>> getAllPolls() {
        return ResponseEntity.ok(this.pollService.getAll());
    }


}
