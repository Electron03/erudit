package com.game.erudut.model;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Table(name = "question")
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;      // Уникальный идентификатор вопроса
    private String questionText;
    @ManyToMany
    @JoinTable(name= "question_answers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;// Текст вопроса
    private Map<Long, String> options;   // Варианты ответа (4 варианта)
    private Long correctAnswer;   // Правильный ответ
    // Уровень сложности (например, от 1 до 3)

    // Конструкторы
    public Question() {
    }

    public Question(Long questionId, String questionText, Map<Long, String> options, Long correctAnswer) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    // Геттеры и сеттеры
    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Map<Long, String> getOptions() {
        return options;
    }

    public void setOptions(Map<Long, String> options) {
        this.options = options;
    }

    public Long getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Long correctAnswer) {
        this.correctAnswer = correctAnswer;
    }


}
