package com.game.erudut.model;

public class Answer {
    private String playerName;   // Имя игрока, который дал ответ
    private Long roomId;       // Идентификатор комнаты (если нужно привязать ответ к сессии)
    private Long questionId;   // Идентификатор вопроса, на который был дан ответ
    private Long answerId;   // Текст ответа игрока
    private boolean isCorrect;   // Был ли ответ правильным (можно использовать для проверки на сервере)

    // Конструкторы
    public Answer() {}

    public Answer(String playerName, Long roomId, Long questionId, Long answerId) {
        this.playerName = playerName;
        this.roomId = roomId;
        this.questionId = questionId;
        this.answerId = answerId;
    }

    // Геттеры и сеттеры
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}

