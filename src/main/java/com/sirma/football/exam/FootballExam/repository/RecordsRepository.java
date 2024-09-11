package com.sirma.football.exam.FootballExam.repository;

import com.sirma.football.exam.FootballExam.model.Records;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface responsible for managing data for the 'Records' entity
 */
@Repository
public interface RecordsRepository extends JpaRepository<Records, Integer> {
    List<Records> findByMatchId(int matchId);

}