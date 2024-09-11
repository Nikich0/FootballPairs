package com.sirma.football.exam.FootballExam.service;

import com.sirma.football.exam.FootballExam.model.Players;
import com.sirma.football.exam.FootballExam.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service class that handles the logic for managing players
 * It interacts with the corresponding repository to perform CRUD operations
 */
@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Players savePlayer(Players player) {
        return playerRepository.save(player);

    }

    public Optional<Players> findPlayerById(int id) {
        return playerRepository.findById(id);

    }

    public List<Players> findAllPlayers() {
        return playerRepository.findAll();

    }

    public Players updatePlayer(int id, Players playerDetails) {
        Optional<Players> playerOptional = playerRepository.findById(id);
        if (playerOptional.isPresent()) {
            Players player = playerOptional.get();
            player.setTeamNumber(playerDetails.getTeamNumber());
            player.setPosition(playerDetails.getPosition());
            player.setFullName(playerDetails.getFullName());
            player.setTeamId(playerDetails.getTeamId());
            return playerRepository.save(player);
        } else {
            throw new RuntimeException("Player does not exist");
        }

    }

    public void deletePlayer(int id) {
        playerRepository.deleteById(id);

    }

}
