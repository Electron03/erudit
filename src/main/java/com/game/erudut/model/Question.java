package com.game.erudut.model;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

@Table(name = "question")
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;      // Уникальный идентификатор вопроса
    private String questionText;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answers> answers;// Текст вопроса// Варианты ответа (4 варианта)
    private int correct;   // Правильный ответ


    // Конструкторы
    public Question() {
    }

    public Question(Long questionId, String questionText, List<Answers>answers, int correct) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.answers = answers;
        this.correct = correct;
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

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public List<Answers> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answers> answers) {
        this.answers = answers;
    }
}
