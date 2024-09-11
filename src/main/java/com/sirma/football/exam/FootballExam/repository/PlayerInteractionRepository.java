package com.sirma.football.exam.FootballExam.repository;

import com.sirma.football.exam.FootballExam.model.PlayerInteractions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface responsible for managing data for the 'PlayerInteractions' entity
 */
@Repository
public interface PlayerInteractionRepository extends JpaRepository<PlayerInteractions, Integer> {

    /**
     * Method used to retrieve a list of 'PlayerInteractions' entities from the database
     * ordered by minutes played together, id of the A player, id of the B player
     */
    @Query("SELECT p FROM PlayerInteractions p ORDER BY p.minutesPlayedTogether DESC, p.aPlayerId ASC, p.bPlayerId ASC")
    List<PlayerInteractions> findAllOrdered();

}