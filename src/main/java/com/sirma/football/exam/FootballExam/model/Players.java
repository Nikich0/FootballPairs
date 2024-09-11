package com.sirma.football.exam.FootballExam.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

/**
 * JPA Entity
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "players", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Players {

    @Id
    private int id;

    private int teamNumber;

    private String Position;

    private String fullName;

    private int teamId;

}