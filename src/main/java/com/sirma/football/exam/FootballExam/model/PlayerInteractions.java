package com.sirma.football.exam.FootballExam.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA Entity
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "player_interactions")
public class PlayerInteractions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int aPlayerId;
    private int bPlayerId;
    private int minutesPlayedTogether;

    public PlayerInteractions(int aPlayerId, int bPlayerId, int minutesPlayedTogether) {
        this.aPlayerId = aPlayerId;
        this.bPlayerId = bPlayerId;
        this.minutesPlayedTogether = minutesPlayedTogether;

    }

}