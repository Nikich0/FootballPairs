package com.sirma.football.exam.FootballExam.service;

import com.sirma.football.exam.FootballExam.model.Matches;
import com.sirma.football.exam.FootballExam.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class that handles the logic for managing football matches
 * It interacts with the corresponding repository to perform CRUD operations
 */
@Service
public class MatchService {
    @Autowired
    private MatchRepository matchRepository;

    public Matches saveMatch(Matches match) {
        return matchRepository.save(match);

    }

    public Optional<Matches> findMatchById(int id) {
        return matchRepository.findById(id);

    }

    public List<Matches> findAllMatches() {
        return matchRepository.findAll();

    }

    public Matches updateMatch(int id, Matches matchDetails) {
        Optional<Matches> matchOptional = matchRepository.findById(id);
        if (matchOptional.isPresent()) {
            Matches match = matchOptional.get();
            match.setATeamId(matchDetails.getATeamId());
            match.setBTeamId(matchDetails.getBTeamId());
            match.setDateOfMatch(matchDetails.getDateOfMatch());
            match.setScore(matchDetails.getScore());
            return matchRepository.save(match);
        } else {
            throw new RuntimeException("Player does not exist");
        }

    }

    public void deleteMatch(int id) {
        matchRepository.deleteById(id);

    }

}
