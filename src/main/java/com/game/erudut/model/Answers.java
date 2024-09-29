package com.game.erudut.model;

import jakarta.persistence.*;

@Table(name = "answers")
@Entity
public class Answers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String answer;

}
