package com.sirma.football.exam.FootballExam.service;

import com.sirma.football.exam.FootballExam.model.Teams;
import com.sirma.football.exam.FootballExam.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class that handles the logic for managing teams
 * It interacts with the corresponding repository to perform CRUD operations
 */
@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public Teams saveTeam(Teams team) {
        return teamRepository.save(team);

    }

    public Optional<Teams> findTeamById(int id) {
        return teamRepository.findById(id);

    }

    public List<Teams> findAllTeams() {
        return teamRepository.findAll();

    }

    public Teams updateTeam(int id, Teams teamDetails) {
        Optional<Teams> TeamOptional = teamRepository.findById(id);
        if (TeamOptional.isPresent()) {
            Teams team = TeamOptional.get();
            team.setName(teamDetails.getName());
            team.setManagerName(teamDetails.getManagerName());
            team.setFootballGroup(teamDetails.getFootballGroup());
            return teamRepository.save(team);
        } else {
            throw new RuntimeException("Team does not exist");
        }

    }

    public void deleteTeam(int id) {
        teamRepository.deleteById(id);

    }

}
