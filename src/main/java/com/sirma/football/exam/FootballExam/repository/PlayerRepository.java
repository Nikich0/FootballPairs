package com.sirma.football.exam.FootballExam.repository;

import com.sirma.football.exam.FootballExam.model.Players;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface responsible for managing data for the 'Players' entity
 */
@Repository
public interface PlayerRepository extends JpaRepository<Players, Integer> {

}
