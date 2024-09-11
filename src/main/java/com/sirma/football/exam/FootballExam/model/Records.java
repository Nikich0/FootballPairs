package com.sirma.football.exam.FootballExam.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA Entity
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "records", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Records {

    @Id
    private int id;

    private int playerId;

    private int matchId;

    private int fromMinutes;

    private int toMinutes;

}