package com.sirma.football.exam.FootballExam.repository;

import com.sirma.football.exam.FootballExam.model.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface responsible for managing data for the 'Teams' entity
 */
@Repository
public interface TeamRepository extends JpaRepository<Teams, Integer> {

}