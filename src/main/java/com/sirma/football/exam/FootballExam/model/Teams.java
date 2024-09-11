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
@Table(name = "teams", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Teams {

    @Id
    private int id;

    private String name;

    private String managerName;

    private String footballGroup;

}