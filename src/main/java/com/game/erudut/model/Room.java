package com.game.erudut.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Table(name = "rooms")
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)// Уникальный идентификатор комнаты
    private List<Player> players = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = true)
    private List<Question> questions = new ArrayList<>();    // Список вопросов для текущей игры
    private int currentQuestionIndex;     // Индекс текущего вопроса
    private boolean isGameActive;         // Состояние игры (активна или завершена)

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public List<Player> getPlayers() {
        return players;
    }
    // Метод для получения игрока по имени
    public Player getPlayerByName(String playerName) {
        // Используем Stream API для поиска игрока по имени
        Optional<Player> player = players.stream()
                .filter(p -> p.getName().equals(playerName)) // Сравниваем имена
                .findFirst(); // Находим первый соответствующий элемент

        // Возвращаем найденного игрока или выбрасываем исключение, если игрок не найден
        return player.orElseThrow(() -> new RuntimeException("Игрок с именем " + playerName + " не найден"));
    }
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }

    public boolean isGameActive() {
        return isGameActive;
    }

    public void setGameActive(boolean gameActive) {
        isGameActive = gameActive;
    }

    // Методы для работы с игроками
    public void addPlayer(Player player) {
        if (players.size() < 4) {  // Ограничение на количество игроков в комнате (до 4 игроков)
            players.add(player);
        } else {
            throw new RuntimeException("Комната уже заполнена.");
        }
    }

    // Получение текущего вопроса
    public Question getCurrentQuestion() {
        if (questions.isEmpty() || currentQuestionIndex >= questions.size()) {
            return null;
        }
        return questions.get(currentQuestionIndex);
    }

    // Переход к следующему вопросу
    public void nextQuestion() {
        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++;
        } else {
            isGameActive = false;  // Игра завершена, если вопросы закончились
        }
    }
}
