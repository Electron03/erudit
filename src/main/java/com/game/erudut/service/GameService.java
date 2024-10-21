package com.game.erudut.service;

import com.game.erudut.model.Player;
import com.game.erudut.model.Question;
import com.game.erudut.model.Room;
import com.game.erudut.repository.PlayerRepository;
import com.game.erudut.repository.QuestionRepository;
import com.game.erudut.repository.RoomRepository;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private RoomRepository roomRepository;
    public ResponseEntity<?> processAnswer(Message message) {

        return ResponseEntity.ok("Ответ успешно обработан");
    }
    public QuestionRepository questionRepository;
    private PlayerRepository playerRepository;

    public void updatePlayerScore(String playerName, Long roomId) {
        Room room = roomRepository.getById(roomId);
        List<Player> players = room.getPlayers();
        Player player = players.stream()
                .filter(p -> p.getName().equals(playerName)) // Условие фильтрации по имени
                .findFirst() // Получаем первого игрока, который соответствует условию
                .orElseThrow(() -> new IllegalArgumentException("Player not found")); // Исключение, если игрок не найден
        player.setScore(player.getScore() + 10); // Добавляем 10 баллов за правильный ответ
    }

    public boolean checkAnswer(Long questionId, int answerId) {
        int correctAnswer = findCorrectAnswerByQuestionId(questionId); // Поиск правильного ответа
        if(answerId==correctAnswer)return true;
        else return false;
    }

    public int findCorrectAnswerByQuestionId(Long questionId) {
        // Логика получения вопроса и правильного ответа
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Вопрос не найден"));
        return question.getCorrect();
    }
}

