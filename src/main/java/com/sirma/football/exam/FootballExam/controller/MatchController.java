package com.sirma.football.exam.FootballExam.controller;


import com.sirma.football.exam.FootballExam.model.Matches;
import com.sirma.football.exam.FootballExam.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CRUD operations for football matches
 */
@RestController
@RequestMapping("/football/upload/matches")
public class MatchController {

    @Autowired
    private MatchService matchRepository;

    @PostMapping
    public ResponseEntity<Matches> createMatch(@RequestBody Matches match) {
        Matches newMatch = matchRepository.saveMatch(match);
        return ResponseEntity.ok(newMatch);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Matches> getMatchById(@PathVariable int id) {
        Optional<Matches> match = matchRepository.findMatchById(id);
        if (match.isPresent()) {
            return ResponseEntity.ok(match.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping
    public ResponseEntity<List<Matches>> getAllMatches() {
        List<Matches> match = matchRepository.findAllMatches();
        return ResponseEntity.ok(match);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Matches> updateMatch(@PathVariable int id, @RequestBody Matches matchDetails) {
        try {
            Matches updatedMatch = matchRepository.updateMatch(id, matchDetails);
            return ResponseEntity.ok(updatedMatch);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable int id) {
        matchRepository.deleteMatch(id);
        return ResponseEntity.noContent().build();

    }

}
