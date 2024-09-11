package com.sirma.football.exam.FootballExam.controller;

import com.sirma.football.exam.FootballExam.model.Players;
import com.sirma.football.exam.FootballExam.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST controller for managing CRUD operations for players
 */
@RestController
@RequestMapping("/football/upload/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping
    public ResponseEntity<Players> createPlayer(@RequestBody Players player) {
        Players newPlayer = playerService.savePlayer(player);
        return ResponseEntity.ok(newPlayer);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Players> getPlayerById(@PathVariable int id) {
        Optional<Players> player = playerService.findPlayerById(id);
        if (player.isPresent()) {
            return ResponseEntity.ok(player.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping
    public ResponseEntity<List<Players>> getAllPlayers() {
        List<Players> players = playerService.findAllPlayers();
        return ResponseEntity.ok(players);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Players> updatePlayer(@PathVariable int id, @RequestBody Players playerDetails) {
        try {
            Players updatedPlayer = playerService.updatePlayer(id, playerDetails);
            return ResponseEntity.ok(updatedPlayer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable int id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();

    }

}
