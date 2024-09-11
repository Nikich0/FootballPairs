package com.sirma.football.exam.FootballExam.repository;

import com.sirma.football.exam.FootballExam.model.Matches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface responsible for managing data for the 'Matches' entity
 */
@Repository
public interface MatchRepository extends JpaRepository<Matches, Integer> {

}
