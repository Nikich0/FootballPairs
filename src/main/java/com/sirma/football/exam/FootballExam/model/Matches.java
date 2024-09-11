package com.sirma.football.exam.FootballExam.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

/**
 * JPA Entity
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "matches", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Matches {

    @Id
    private int id;

    private int aTeamId;

    private int bTeamId;

    private Date dateOfMatch;

    private String score;

}