package com.sirma.football.exam.FootballExam.controller;


import com.sirma.football.exam.FootballExam.model.Teams;
import com.sirma.football.exam.FootballExam.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CRUD operations teams
 */
@RestController
@RequestMapping("/football/upload/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping
    public ResponseEntity<Teams> createTeam(@RequestBody Teams team) {
        Teams newTeam = teamService.saveTeam(team);
        return ResponseEntity.ok(newTeam);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Teams> getTeamById(@PathVariable int id) {
        Optional<Teams> team = teamService.findTeamById(id);
        if (team.isPresent()) {
            return ResponseEntity.ok(team.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping
    public ResponseEntity<List<Teams>> getAllTeams() {
        List<Teams> teams = teamService.findAllTeams();
        return ResponseEntity.ok(teams);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Teams> updateTeam(@PathVariable int id, @RequestBody Teams teamDetails) {
        try {
            Teams updatedTeam = teamService.updateTeam(id, teamDetails);
            return ResponseEntity.ok(updatedTeam);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable int id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();

    }

}
